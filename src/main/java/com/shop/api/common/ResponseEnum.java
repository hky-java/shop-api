package com.shop.api.common;

public enum ResponseEnum {
     CART_IS_NO(201,"没有购物车"),
    CART_NUM_IS_ERROR(3002,"商品数量不合法"),
    CART_PRODUCT_IS_DOWN(3001,"商品已下架"),
    CART_PRODUCT_IS_NULL(3000,"添加的商品不存在"),

    LOGIN_TIME_OUT(2006,"登陆超时"),
    LOGIN_MEMBER_IS_CHANGE(2005,"会员信息被篡改"),
    LOGIN_HEADER_CONTENT_IS_MISS(2004,"头信息不完整"),
    LOGIN_HEADER_MISS(2003,"头信息丢失"),
    LOGIN_PASSWORD_ERROR(2002,"密码错误"),
    LOGIN_NAME_NOT_EXIT(2001,"用户名不存在"),
    LOGIN__INFO_IS_NULL(2000,"用户名或密码为空"),


    REG_MAIL_IS_EXIST(1003,"邮箱已存在"),
    REG_PHONE_IS_EXIST(1002,"手机号已存在"),
    REG_MEMBER_IS_EXIST(1001,"会员名已存在"),
   REG_MEMBER_IS_NULL(1000,"注册会员信息为空了");

    private int code;
    private String msg;

    private ResponseEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
