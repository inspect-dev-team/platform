package com.platform.service.impl;

import com.platform.constants.CarpoolConstant;
import com.platform.constants.CommonConstant;
import com.platform.constants.TemplateMessageConstant;
import com.platform.dao.CarpoolOrderMapper;
import com.platform.dao.CarpoolPublishMapper;
import com.platform.dao.CarpoolUserMapper;
import com.platform.dto.CarpoolPublishVo;
import com.platform.entity.CarpoolOrder;
import com.platform.entity.CarpoolPublish;
import com.platform.entity.CarpoolUser;
import com.platform.service.ApiCarpoolOrderService;
import com.platform.service.CarpoolPublishService;
import com.platform.utils.DateUtils;
import com.platform.utils.GEOUtils;
import com.platform.utils.StringUtils;
import com.platform.weixin.templateMessage.WechatTemplateMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zuimeideshiguang on 18/2/12.
 */
@Service("carpoolPublishService")
public class CarpoolPublishServiceImpl extends BasicSetServiceImpl<CarpoolPublish> implements CarpoolPublishService {

    @Autowired
    private CarpoolPublishMapper carpoolPublishMapper;

    @Autowired
    private CarpoolOrderMapper carpoolOrderMapper;

    @Autowired
    private CarpoolUserMapper carpoolUserMapper;

    @Autowired
    private ApiCarpoolOrderService apiCarpoolOrderService;

    @Override
    public Map<String, Object> queryPublishListByPage(CarpoolPublishVo carpoolPublish) {
        List<Object> geos = null;

        ///起点地理查询范围设置
        if(carpoolPublish.getStartPointLatitude() != null && carpoolPublish.getStartPointLongitude() != null){
            carpoolPublish.setCustLatitude(carpoolPublish.getStartPointLatitude());
            carpoolPublish.setCustLongitude(carpoolPublish.getStartPointLongitude());
        }
        if (carpoolPublish.getCustLatitude() != null && carpoolPublish.getCustLongitude() != null){
            geos = GEOUtils.createGeoCodes(carpoolPublish.getCustLongitude(),carpoolPublish.getCustLatitude());
        }
        carpoolPublish.setGeos(geos); // 设置地理查询范围

        ///设置终点地理范围
    /*    if (carpoolPublish.getDestinationLatitude() != null && carpoolPublish.getDestinationLongitude() != null){
            carpoolPublish.setDestinationGeos(GEOUtils.createGeoCodes(carpoolPublish.getDestinationLongitude(),carpoolPublish.getDestinationLatitude()));
        }*/

        List<CarpoolPublish> datas = carpoolPublishMapper.selectTrips(carpoolPublish);
        Map<String, Object> returnMap = new HashMap<>();
        //设置返回参数
        int total = datas != null ? datas.size() : 0;
        int page = total / carpoolPublish.getLimit().intValue() + ((total % carpoolPublish.getLimit().intValue() == 0) ? 0 : 1);
        returnMap.put("totalPage",page);
        returnMap.put("list", pageAndSort(carpoolPublish,datas));
        return returnMap;
    }

    @Override
    public void cnacelPublish(CarpoolPublish carpoolPublish) {
        CarpoolOrder order = new CarpoolOrder();
        order.setPublishId(carpoolPublish.getId());
        order.setStatus(CarpoolConstant.ORDER_SUCCESS_STATUS);
        order.setDataStatus(CommonConstant.USEABLE_STATUS);
        List<CarpoolOrder> carpoolOrders = carpoolOrderMapper.select(order); // 预约成功的订单
        int rs = carpoolPublishMapper.updateByPrimaryKeySelective(carpoolPublish);
        if (rs > 0 && carpoolOrders != null && carpoolOrders.size() > 0){ // 发送模板消息
            for (CarpoolOrder carpoolOrder : carpoolOrders){
                apiCarpoolOrderService.sendTemplateMsg(carpoolOrder.getOrderUserId(), TemplateMessageConstant.CARPOOL_PUBLISH_CANCEL_TMPL_ID,"",cancelMsgData(carpoolPublish,carpoolOrder));
            }

        }
    }

    ///预约取消通知封装
    private TreeMap<String,TreeMap<String,String>> cancelMsgData(CarpoolPublish carpoolPublish,CarpoolOrder carpoolOrder){
        TreeMap<String,TreeMap<String,String>> params = new TreeMap<String,TreeMap<String,String>>();
        List<TreeMap<String,String>> list = new ArrayList<>();

        CarpoolUser user = new CarpoolUser();
        user.setId(carpoolOrder.getOrderUserId());
        CarpoolUser carpoolUser = carpoolUserMapper.selectOne(user);
        if (carpoolUser != null){
            list.add(WechatTemplateMsg.item(StringUtils.NullToString(carpoolUser.getNickName(),"--"), "#000000")); //昵称
            list.add(WechatTemplateMsg.item(StringUtils.NullToString(carpoolOrder.getMobile(),"--"), "#000000"));//手机号
            list.add(WechatTemplateMsg.item(DateUtils.format(carpoolOrder.getDepartureTime(),DateUtils.DATE_TIME_PATTERN), "#000000"));//日期
            list.add(WechatTemplateMsg.item(carpoolOrder.getStartPoint() +"-"+ carpoolOrder.getDestination(), "#000000"));//路线
            list.add(WechatTemplateMsg.item(StringUtils.NullToString(carpoolOrder.getCancelReason(),"对方时间有变，已取消行程，请选择其他交通工具"), "#000000"));//温馨提示
        }


        if(list.size() < 0) return  null;
        for (int i = 0 ; i < list.size() ; i++ ){
            params.put("keyword"+(i+1),list.get(i));
        }
        return params;
    }

    /**
     * 分页加排序
     * @param carpoolPublish
     * @param datas
     * @return
     */
    private  List<CarpoolPublish> pageAndSort(CarpoolPublishVo carpoolPublish ,  List<CarpoolPublish> datas){
        if(null == datas) return null;
        List<CarpoolPublish> list = new ArrayList<>();
        if( null != datas && datas.size() > 0){
            ///填充距离
            for(CarpoolPublish po : datas){
                po.setDistance(GEOUtils.calcDistance(carpoolPublish.getCustLongitude(),carpoolPublish.getCustLatitude(),po.getStartPointLongitude(),po.getStartPointLatitude()));
            }
            //根据距离排序
            Collections.sort(datas, new Comparator<CarpoolPublish>() {
                public int compare(CarpoolPublish o1, CarpoolPublish o2) {
                    Double distanceA = o1.getDistance();
                    Double distanceB = o2.getDistance();
                    // 升序
                    return distanceA.compareTo(distanceB);
                }
            });
            if (datas.size() < carpoolPublish.getLimit().intValue()){
                return  datas;
            }
            //分页
            int startIndex = 0;
            int endIndex = 0;
            if(carpoolPublish.getStart() == 0){
                startIndex = 0;
                endIndex = carpoolPublish.getLimit();
            }else  if(carpoolPublish.getStart() == 1){
                startIndex = 0;
                endIndex =  carpoolPublish.getLimit() ;
            }else{
                startIndex = (carpoolPublish.getStart() - 1) *  carpoolPublish.getLimit();
                endIndex = carpoolPublish.getStart() *  carpoolPublish.getLimit() ;
            }
            if (datas.size() > 0 && datas.size() <= carpoolPublish.getStart() *  carpoolPublish.getLimit() ) endIndex = datas.size();
            if (startIndex > datas.size())return null;//防止前端错误传递start参数
            list =  datas.subList(startIndex,endIndex);

        }
        return 	list;
    }
}