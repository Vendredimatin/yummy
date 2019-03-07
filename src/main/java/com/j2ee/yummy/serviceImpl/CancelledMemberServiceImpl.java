package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.Repository.CancelledMemberRepository;
import com.j2ee.yummy.model.CancelledMember;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.service.CancelledMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @program: yummy
 * @description: 已注销会员的业务逻辑实现类
 * @author: Liu Hanyi
 * @create: 2019-02-07 20:14
 **/

@Service
public class CancelledMemberServiceImpl implements CancelledMemberService {
    @Autowired
    CancelledMemberRepository cancelledMemberRepository;

    @Override
    public CancelledMember add(Member member) {
        CancelledMember cancelledMember = new CancelledMember();
        cancelledMember.setMemberID(member.getId());
        cancelledMember.setEmail(member.getEmail());
        return cancelledMemberRepository.save(cancelledMember);
    }

    @Override
    public boolean isCancelled(String email) {
        return cancelledMemberRepository.existsByEmail(email);
    }
}
