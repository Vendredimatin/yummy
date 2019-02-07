package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.PO.MemberPO;
import com.j2ee.yummy.POVOChanger.POVOChanger;
import com.j2ee.yummy.dao.MemberDao;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @program: yummy
 * @description: 会员类的业务逻辑类
 * @author: Liu Hanyi
 * @create: 2019-02-04 10:08
 **/
@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;

    @Override
    public Member login(String email, String password) {
        MemberPO member = memberDao.login(email, password);

        return POVOChanger.toMember(member);
    }

    @Override
    public String update(Member member) {
        MemberPO memberPO = POVOChanger.toMemberPO(member);
        return memberDao.update(memberPO) ? "success" : "fail";
    }

    @Override
    public String register(String email, String password) {
        MemberPO member = new MemberPO();
        member.setEmail(email);
        member.setPassword(password);
        return memberDao.insert(member) ? "success" : "fail";
    }

    @Override
    public Member getMemberByID(long id) {
        MemberPO memberPO = memberDao.getMemberByID(id);
        Member member = POVOChanger.toMember(memberPO);

        return member;
    }
}
