package com.platform.controller.app;

import com.platform.service.enterprise.IEnterpriseService;
import com.platform.utils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created with IntelliJ IDEA
 * ProjectName: inspect
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/8/19 14:19
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

@Controller
@RequestMapping("/app")
public class AppEnterpriseController {

    @Autowired
    private IEnterpriseService enterpriseService;

    @RequestMapping("/enterprise/search")
    @ResponseBody
    public R enterpriseSearch(@RequestParam Map<String, Object> queryParams){
        return R.succeed().put("page",enterpriseService.search(queryParams));
    }
}