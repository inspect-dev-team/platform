package com.platform.utils;

import com.platform.utils.enums.SystemCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author admin
 *
 * @date 2016年10月27日 下午9:59:27
 */
public class R extends HashMap<String, Object> {
    private static final long serialVersionUID = 1L;

    public R() {
        put("code", 0);
        put("msg", "success");
    }

    public static R error() {
        return error(500, "未知异常，请联系管理员");
    }

    public static R error(String msg) {
        return error(500, msg);
    }

    public static R error(int code, String msg) {
        R r = new R();
        r.put("code", code);
        r.put("msg", msg);
        return r;
    }

    public static R loginError(){
        return error(SystemCode.LOGIN_VALID.getCode(),SystemCode.LOGIN_VALID.getDesc());
    }

    public static R succeed(){
        return error(ErrorCode.SUCCEED.getCode(),ErrorCode.SUCCEED.getDesc());
    }

    public static R failure(){
        return error(ErrorCode.FAILURE.getCode(),ErrorCode.FAILURE.getDesc());
    }

    public static R paramsIllegal(){
        return error(SystemCode.PARAM_ILLEGAL.getCode(),SystemCode.PARAM_ILLEGAL.getDesc());
    }

    public static R ok(String msg) {
        R r = new R();
        r.put("msg", msg);
        return r;
    }

    public static R ok(Map<String, Object> map) {
        R r = new R();
        r.putAll(map);
        return r;
    }

    public static R ok() {
        return new R();
    }

    public static R appOk() {

        R r = new R();
        r.put("code", 200);
        r.put("msg", "success");
        return r;
    }

    public R put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
