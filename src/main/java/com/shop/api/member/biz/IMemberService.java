package com.shop.api.member.biz;

import com.shop.api.common.ServerResponse;
import com.shop.api.member.po.Member;

public interface IMemberService {

    ServerResponse addMember(Member member);

    ServerResponse validateMemberName(String memberName);

    ServerResponse validatePhone(String phone);

    ServerResponse validateMail(String mail);

    ServerResponse login(String memberName, String password);
}
