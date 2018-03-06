package com.platform.api;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.annotation.IgnoreAuth;
import com.platform.annotation.LoginUser;
import com.platform.constants.CommonConstant;
import com.platform.entity.Feedback;
import com.platform.entity.FeedbackVo;
import com.platform.entity.UserVo;
import com.platform.service.ApiFeedbackService;
import com.platform.service.IFeedbackService;
import com.platform.util.ApiBaseAction;
import com.platform.vo.RequestPageParameter;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 作者: @author bjsonghongxu <br>
 * 时间: 2017-08-11 08:32<br>
 * 描述: ApiFeedbackController <br>
 */
@RestController
@RequestMapping("/api/feedback")
public class ApiFeedbackController extends ApiBaseAction {
    @Autowired
    private ApiFeedbackService feedbackService;

    @Autowired
    private IFeedbackService iFeedbackService;

    /**
     * 添加反馈
     */
    @RequestMapping("save")
    public Object save(@LoginUser UserVo loginUser, @RequestBody FeedbackVo feedbackVo) {
        feedbackVo.setUser_id(loginUser.getUserId());
        feedbackVo.setUser_name(loginUser.getNickname());
        feedbackVo.setMsg_time(System.currentTimeMillis() / 1000);
        feedbackService.save(feedbackVo);
        return toResponsSuccess("感谢你的反馈");
    }

    /**
     * 反馈
     * @param feedback
     * @return
     */
    @IgnoreAuth
    @RequestMapping(value = "/feedback")
    public Object feedback(Feedback feedback) {
        feedback.setFeedbackTime(new Date()); // 反馈时间
        iFeedbackService.insertSelective(feedback);
        return  toResponsSuccess("感谢您的反馈");
    }

    /**
     * 查询反馈
     * @param feedback
     * @return
     */
    @IgnoreAuth
    @RequestMapping(value = "/queryFeedback")
    public Object queryFeedback( @RequestBody Feedback feedback) {
        Map<String,Object> returnMap = new HashedMap();
       // PageHelper.startPage(requestPageParameter.getStart(),requestPageParameter.getLimit(),true); //设置分页
        feedback.setDataStatus(CommonConstant.USEABLE_STATUS);
        List<Feedback> feedbacks = iFeedbackService.select(feedback);
        //PageInfo<Feedback> pageInfo = new PageInfo<>(feedbacks);
        setFeedbackReply(feedbacks);
        //returnMap.put("total", feedbacks.size());
        returnMap.put("list", feedbacks);

        return  toResponsSuccess(returnMap);
    }


    //设置回复
    private void setFeedbackReply(List<Feedback> feedbacks){
        if (null != feedbacks && feedbacks.size() > 0){
            Feedback f = null;
            for (Feedback feedback : feedbacks){
                f = new Feedback();
                f.setType(2);
                f.setReplyTo(feedback.getId());
                f.setDataStatus(CommonConstant.USEABLE_STATUS);
                feedback.setReplyList(iFeedbackService.select(f));
            }
        }
    }





}