package com.j2ee.yummy.service;

import com.j2ee.yummy.model.CancelledMember;
import com.j2ee.yummy.model.Member;

public interface CancelledMemberService {

    public CancelledMember add(Member member);

    public boolean isCancelled(String email);
}
