package com.gyg.config.security;


public enum Systemcode {

    /**
     * OK
     */
    OK(1,"成功"),

    /**
     * AccessTokenError
     */
    AccessTokenError(400,"令牌失效"),

    /**
     *UNAUTHORIZED
     */
    UNAUTHORIZED(401,"用户未登录"),

    /**
     * AuthError
     */
    AuthError(402,"用户密码错误"),

    /**
     *InnerError
     */
    InnerError(500,"系统内部错误"),

    /**
     *ParameterValidError
     */
    ParameterValidError(501,"参数错误"),

    /**
     *AccessDenied
     */
    AccessDenied(502,"用户没有权限");

    int code;
    String message;
    public int getCode() {
        return code;
    }
    public Systemcode setCode(int code) {
        this.code = code;
        return  this;
    }
    public String getMessage() {
        return message;
    }

    public Systemcode setMessage(String message) {
        this.message = message;
        return this;
    }

    Systemcode() {
    }

    Systemcode(int code,String message) {
    }


}
