package com.shop.api.util;

public class KeyUtil {

    public static final int MEMBER_KEY_EXPIRE = 5*60;

    public static String buildMemberKey(String uuid,Long memberId){
        return "member:"+uuid+":"+memberId;
    }

    public static String buildCartKey(Long memberId) {
        return "cart:"+memberId;
    }

    public static String buildStockLess(Long memberId) {
        return "order:stock:less:"+memberId; //前面的字符串为自定义
    }
}
