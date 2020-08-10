package com.shop.api.order.po;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class Order implements Serializable {
    //因为之前的主键是在数据库中设置自增的，而此处不是，而是设置值，因此加如下注解
   private   String id;
   private   Long userId;
   private   Date createTime;
   private   Date payTime;
   private   int status;
   private   BigDecimal totalPrice;
   private   int totalNum;
   private   int payType;
   private   Long recipientid;
   private   String recipientor;
   private    String address;
   private String phone;
}
