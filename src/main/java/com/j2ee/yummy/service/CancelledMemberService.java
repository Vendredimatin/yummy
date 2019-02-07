package com.j2ee.yummy.service;

import com.j2ee.yummy.model.CancelledMember;

public interface CancelledMemberService {

    public boolean add(CancelledMember cancelledMember);

    public boolean isCancelled(long id);
}
