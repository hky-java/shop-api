package com.shop.api.order.po;


import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class OrderItem implements Serializable {
private Long id;
private String orderId;
private Long userId;
private Long productId;
private String productName;
private String imageUrl;
private BigDecimal price;
private int num;
}
