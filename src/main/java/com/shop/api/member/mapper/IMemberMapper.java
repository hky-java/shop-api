package com.shop.api.member.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.shop.api.member.po.Member;
import org.apache.ibatis.annotations.Insert;


public interface IMemberMapper extends BaseMapper<Member> {
    @Insert("insert into t_member(memberName,password,realName,birthday,mail,phone,shengId,shiId,XianId,areaName) " +
            "values(#{memberName},#{password},#{realName},#{birthday},#{mail},#{phone},#{shengId},#{shiId},#{xianId},#{areaName})")
    void addMember(Member member);

}
