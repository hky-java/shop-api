package com.shop.api.order.controller;

import com.shop.api.annotation.Check;
import com.shop.api.common.ServerResponse;
import com.shop.api.common.SystemConstant;

import com.shop.api.member.vo.MemberVo;
import com.shop.api.order.biz.IOrderService;
import com.shop.api.order.param.OrderParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

public class OrderController {

   @Resource(name = "OrderService")
  private IOrderService orderService;



    @GetMapping("/generateOrder")
    @Check
    @ApiOperation("生成订单")
    public ServerResponse generateOrder(HttpServletRequest request, OrderParam orderParam){
        //如下若不强转则返回的是对象
        MemberVo member = (MemberVo)request.getAttribute(SystemConstant.CURR_MEMBER);
        Long id = member.getId();
        orderParam.setMemberId(id);
        return orderService.generateOrder(orderParam);
    }

}
