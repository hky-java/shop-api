package com.shop.api.cart.controller;

import com.shop.api.annotation.Check;
import com.shop.api.cart.biz.ICartService;
import com.shop.api.common.ServerResponse;
import com.shop.api.common.SystemConstant;
import com.shop.api.member.vo.MemberVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/cart")
@Api(tags = "购物车接口")
public class CartController {

    @Resource(name = "cartService")
    private ICartService cartService;

    @PostMapping("/addItem")
    @Check
    @ApiOperation("添加商品到购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "头信息",required = true,type = "string",paramType = "header"),
            @ApiImplicitParam(name = "goodsId",value = "商品id",required = true,type = "long",paramType = "query"),
            @ApiImplicitParam(name = "num",value = "商品数量",required = true,type = "int",paramType = "query"),
    })
    public ServerResponse addItem(HttpServletRequest request,Long goodsId,int num){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        Long memberId = member.getId();
        return cartService.addItem(memberId, goodsId, num);
    }

    @GetMapping("/findItemList")
    @Check
    @ApiOperation("获取指定会员对应的购物车")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "x-auth",value = "头信息",required = true,type = "string",paramType = "header")
    })
    public ServerResponse findItemList(HttpServletRequest request){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        Long memberId = member.getId();
        return cartService.findItemList(memberId);
    }


    @GetMapping("/findNum")
    @Check
    public ServerResponse findNum(HttpServletRequest request){
        MemberVo member = (MemberVo) request.getAttribute(SystemConstant.CURR_MEMBER);
        Long memberId = member.getId();
        return cartService.findNum(memberId);
    }





}
