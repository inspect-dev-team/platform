package com.platform.controller.app;

import com.platform.entity.inspect.InspectOrderFlowEntity;
import com.platform.service.inspect.IInspectOrderService;
import com.platform.utils.R;
import com.platform.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * ProjectName: inspect
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/7/24 21:06
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */
@Controller
@RequestMapping("/app")
public class AppAnomalyController {

    @Autowired
    private IInspectOrderService inspectOrderService;

    @RequestMapping("/anomaly/list")
    @ResponseBody
    public R anomalyList(@RequestParam Map<String, Object> queryParams){
        if (!checkParams(queryParams)) {
            return R.paramsIllegal();
        }
        return R.succeed().put("page",inspectOrderService.search(queryParams));
    }

    @RequestMapping("/anomaly/process")
    @ResponseBody
    public R process(@ModelAttribute Map<String, Object> queryParams){
        try {
            inspectOrderService.processAnomaly(queryParams);
            return R.succeed();
        } catch (Exception e) {
            return R.failure();
        }
    }

    @RequestMapping("/anomaly/report")
    @ResponseBody
    public R report(@ModelAttribute InspectOrderFlowEntity queryParams){
        try {
            inspectOrderService.report(queryParams);
            return R.succeed();
        } catch (Exception e) {
            return R.failure();
        }

    }

    private boolean checkParams(Map<String, Object> queryParams){
        if (queryParams == null || queryParams.size()==0) {
            return false;
        }else {
            if (StringUtils.isNullOrEmpty(queryParams.get("status"))) {
                return false;
            }
        }
        return true;
    }

}
