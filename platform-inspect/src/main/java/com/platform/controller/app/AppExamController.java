package com.platform.controller.app;

import com.platform.entity.exam.ExamEntity;
import com.platform.entity.exam.ExamMemberEntity;
import com.platform.entity.exam.ExamQuestionEntity;
import com.platform.entity.exam.ExamQuestionItemEntity;
import com.platform.service.exam.ExamMemberService;
import com.platform.service.exam.ExamQuestionItemService;
import com.platform.service.exam.ExamQuestionService;
import com.platform.service.exam.ExamService;
import com.platform.utils.PageUtils;
import com.platform.utils.Query;
import com.platform.utils.R;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * app端考试处理控制器
 *
 * @author bjsonghongxu
 * @create 2018-07-28 16:31
 **/
@Controller
@RequestMapping("/app/exam/")
public class AppExamController {

    private static  final  Logger logger = LoggerFactory.getLogger(AppExamController.class);


    @Autowired
    private ExamService examService;

    @Autowired
    private ExamQuestionService examQuestionService;

    @Autowired
    private ExamQuestionItemService examQuestionItemService;

    @Autowired
    private ExamMemberService examMemberService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params) {

        ///参数校验
        if(null == params.get("userId")){return  R.paramsIllegal(); }

        //查询列表数据
        Query query = new Query(params);

        List<ExamEntity> examList = examService.queryExamListByUserId(query);
        int total = examService.queryExamListByUserIdTotal(query);

        PageUtils pageUtil = new PageUtils(examList, total, query.getLimit(), query.getPage());

        return R.appOk().put("page", pageUtil);
    }


    /**
     * 获取考试试题列表
     * @param id
     * @return
     */
    @RequestMapping("/getQuestionList/{id}")
    @ResponseBody
    public R getQuestionList(@PathVariable("id") Long id) {
        ///参数校验
        if(null == id){return  R.paramsIllegal(); }

        Map<String, Object> params = new HashedMap();
        params.put("examId",id);
        List<ExamQuestionEntity> examQuestionEntities = examQuestionService.queryList(params);
        if (null != examQuestionEntities && examQuestionEntities.size() > 0){
            for (ExamQuestionEntity q : examQuestionEntities) {
                Map<String, Object> p = new HashedMap();
                p.put("questionId",q.getId());
                List<ExamQuestionItemEntity> questionItemEntities = examQuestionItemService.queryList(p);
                if (null != questionItemEntities){
                  q.setQuestionItems(questionItemEntities);
                }
            }
        }
        return R.appOk().put("questionList", examQuestionEntities);
    }


    /**
     * 提交考试结果
     * @param examMemberEntity
     * @return
     */
    @RequestMapping("/subExamResult")
    @ResponseBody
    public R subExamResult(@RequestBody ExamMemberEntity examMemberEntity ) {
        ///参数校验
        if(null == examMemberEntity.getExamId() || null == examMemberEntity.getMemberId()){
            return  R.paramsIllegal();
        }
        try {
            examMemberService.update(examMemberEntity);
        }catch (Exception e){
            return R.error();
        }

        return R.appOk();
    }



}