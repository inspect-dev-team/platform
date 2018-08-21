package com.platform.utils.enums;

/**
 * Created with IntelliJ IDEA
 * ProjectName: inspect
 * CreateUser:  lixiaopeng
 * CreateTime : 2018/8/1 20:24
 * ModifyUser: bjlixiaopeng
 * Class Description: 异常枚举
 * To change this template use File | Settings | File and Code Template
 */


public enum InspectStatusEnum {
    NORMAL(0,"正常"),
    ABNORMAL(1,"异常");

    private Integer code;
    private String desc;

    InspectStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
