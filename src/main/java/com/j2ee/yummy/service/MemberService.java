package com.j2ee.yummy.service;

import com.j2ee.yummy.model.Member;

public interface MemberService {

    public Member login(String email, String password);

    public String update(Member member);

    public String register(String email,String password);

    public Member getMemberByID(long id);
}
