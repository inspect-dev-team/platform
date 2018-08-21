package com.platform.controller.app;

import com.platform.entity.inspect.InspectOrderEntity;
import com.platform.service.common.CommonService;
import com.platform.service.inspect.IInspectOrderService;
import com.platform.service.inspect.InspectOrderFlowService;
import com.platform.utils.R;
import com.platform.utils.enums.DataStatusEnum;
import com.platform.utils.enums.InspectStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created with IntelliJ IDEA
 * ProjectName: inspect
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/8/21 19:35
 * ModifyUser: bjlixiaopeng
 * Class Description:
 * To change this template use File | Settings | File and Code Template
 */

@RestController
@RequestMapping("/app")
public class AppInspectOrderController {

    @Autowired
    private CommonService commonService;

    @Autowired
    private IInspectOrderService inspectOrderService;

    @RequestMapping(value = "/material/check",method = RequestMethod.POST)
    public R materialCheck(@ModelAttribute InspectOrderEntity entity){
        try {
            inspectOrderService.materialCheck(entity);
            return R.succeed();
        } catch (Exception e) {
            return R.failure();
        }
    }

}