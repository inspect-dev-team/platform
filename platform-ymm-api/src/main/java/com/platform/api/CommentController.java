package com.platform.api;

import com.github.abel533.entity.Example;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.platform.constants.CommonConstant;
import com.platform.dto.CommentVo;
import com.platform.entity.ScComment;
import com.platform.entity.ScInfo;
import com.platform.service.ICommentService;
import com.platform.service.IInfoService;
import com.platform.util.ApiBaseAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 评论控制器
 *
 * @author bjsonghongxu
 * @create 2018-06-21 15:01
 **/
@RestController
@RequestMapping("/api/comment")
public class CommentController extends ApiBaseAction {

    public static final Logger logger = LoggerFactory.getLogger(CommentController.class);

    @Autowired
    private IInfoService iInfoService;

    @Autowired
    private ICommentService iCommentService;

    /**
     * 分页查询评论
     * @param commentVo
     * @return
     */
    @RequestMapping("list")
    public Object list(@RequestBody CommentVo commentVo) {
        logger.debug("list ： 参数为 ： " + commentVo.toString());
        PageHelper.startPage(commentVo.getStart(), commentVo.getLimit(), true, false); //设置分页
        Example example = new Example(ScComment.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("dataStatus",CommonConstant.USEABLE_STATUS);
        example.setOrderByClause(" createTime DESC"); ///创建时间倒叙输出
        List<ScComment> list = iCommentService.selectByExample(example);
        PageInfo<ScComment> pageInfo = new PageInfo<>(list);
        Map<String, Object> returnMap = new HashMap<>();
        //设置返回参数
        returnMap.put("total",pageInfo.getPages());
        returnMap.put("list", pageInfo.getList());
        return toResponsSuccess(returnMap);
    }

    /**
     *
     * @param scComment
     * @return
     */
    @RequestMapping("comment")
    public Object comment(@RequestBody ScComment scComment) {
        logger.debug("进入comment ,参数为 ： " + scComment.toString());
        if (null == scComment.getInfoId()) {return toResponsFail("评论失败，信息不完整");}
        try {

            scComment.setDataStatus(CommonConstant.USEABLE_STATUS);
            scComment.setCreateTime(new Date());
            iCommentService.insertSelective(scComment);

            ////增加评论数
            ScInfo scInfo = new ScInfo();
            ScInfo si = new ScInfo(scComment.getInfoId(), CommonConstant.USEABLE_STATUS);
            ScInfo info = iInfoService.selectOne(si);
            if (null != info){
                scInfo.setId(info.getId());
                scInfo.setCommentNum((null != info.getCommentNum()) ? info.getCommentNum().longValue() + 1L : 1L);
                iInfoService.updateByPrimaryKeySelective(scInfo);
            }
        }catch (Exception e){
            logger.error("comment error ", e);
            return toResponsFail("网络原因，评论失败");
        }

        return toResponsSuccess("评论成功");
    }

}
