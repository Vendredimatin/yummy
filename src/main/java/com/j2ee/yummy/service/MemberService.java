package com.j2ee.yummy.service;

import com.j2ee.yummy.model.Member;

public interface MemberService {

    public boolean login(String email, String password);

    public boolean update(Member member);

    public boolean register(Member member);
}
