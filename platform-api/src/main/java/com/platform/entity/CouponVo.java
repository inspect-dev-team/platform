package com.platform.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-15 08:03:41
 */
public class CouponVo implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键
    private Integer id;
    //优惠券名称
    private String name;
    //金额
    private BigDecimal type_money;
    //发放方式 0：按订单发放 1：按用户发放 2:商品转发送券 3：按商品发放 4:新用户注册 5：线下发放 6评价好评红包（固定或随机红包） 7包邮
    private Integer send_type;
    //最小金额
    private BigDecimal min_amount;
    //最大金额
    private BigDecimal max_amount;
    //发放时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date send_start_date;
    //发放时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date send_end_date;
    //使用开始时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date use_start_date;
    //使用结束时间
    @JsonFormat(pattern = "yyyy.MM.dd")
    private Date use_end_date;
    //最小商品金额
    private BigDecimal min_goods_amount;

    private String useDesc; //使用说明;

    //优惠券会员Id
    private String user_id;
    //优惠券编码
    private String coupon_number;
    //可用 1:可用 0：不可用
    private Integer enabled = 0;
    //转发次数
    private Integer min_transmit_num;

    private Integer userCouponId;
    private Integer couponCodeId; //兑换码ID
    private Integer isUsed; //是否被使用
    private Integer isTransmit; //是否转送 0 否 1是

    private Integer isDiscount; // 是否可以折现 0 是 1 否

    private Double discountRate; // 折现率


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getType_money() {
        return type_money;
    }

    public void setType_money(BigDecimal type_money) {
        this.type_money = type_money;
    }

    public Integer getSend_type() {
        return send_type;
    }

    public void setSend_type(Integer send_type) {
        this.send_type = send_type;
    }

    public BigDecimal getMin_amount() {
        return min_amount;
    }

    public void setMin_amount(BigDecimal min_amount) {
        this.min_amount = min_amount;
    }

    public BigDecimal getMax_amount() {
        return max_amount;
    }

    public void setMax_amount(BigDecimal max_amount) {
        this.max_amount = max_amount;
    }

    public Date getSend_start_date() {
        return send_start_date;
    }

    public void setSend_start_date(Date send_start_date) {
        this.send_start_date = send_start_date;
    }

    public Date getSend_end_date() {
        return send_end_date;
    }

    public void setSend_end_date(Date send_end_date) {
        this.send_end_date = send_end_date;
    }

    public Date getUse_start_date() {
        return use_start_date;
    }

    public void setUse_start_date(Date use_start_date) {
        this.use_start_date = use_start_date;
    }

    public Date getUse_end_date() {
        return use_end_date;
    }

    public void setUse_end_date(Date use_end_date) {
        this.use_end_date = use_end_date;
    }

    public BigDecimal getMin_goods_amount() {
        return min_goods_amount;
    }

    public void setMin_goods_amount(BigDecimal min_goods_amount) {
        this.min_goods_amount = min_goods_amount;
    }

    public String getUseDesc() {
        return useDesc;
    }

    public void setUseDesc(String useDesc) {
        this.useDesc = useDesc;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getCoupon_number() {
        return coupon_number;
    }

    public void setCoupon_number(String coupon_number) {
        this.coupon_number = coupon_number;
    }

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public Integer getMin_transmit_num() {
        return min_transmit_num;
    }

    public void setMin_transmit_num(Integer min_transmit_num) {
        this.min_transmit_num = min_transmit_num;
    }

    public Integer getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(Integer couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public Integer getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(Integer isUsed) {
        this.isUsed = isUsed;
    }

    public Integer getIsTransmit() {
        return isTransmit;
    }

    public void setIsTransmit(Integer isTransmit) {
        this.isTransmit = isTransmit;
    }

    public Integer getIsDiscount() {
        return isDiscount;
    }

    public void setIsDiscount(Integer isDiscount) {
        this.isDiscount = isDiscount;
    }

    public Double getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(Double discountRate) {
        this.discountRate = discountRate;
    }

    public Integer getUserCouponId() {
        return userCouponId;
    }

    public void setUserCouponId(Integer userCouponId) {
        this.userCouponId = userCouponId;
    }
}
