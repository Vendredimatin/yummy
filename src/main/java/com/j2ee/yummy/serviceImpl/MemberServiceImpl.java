package com.j2ee.yummy.serviceImpl;


import com.j2ee.yummy.dao.MemberDao;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
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
        Member member = memberDao.login(email, password);

        return member;
    }

    @Override
    public String update(Member member) {
        return memberDao.update(member) ? "success" : "fail";
    }

    @Override
    public String register(String email, String password) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        return memberDao.insert(member) ? "success" : "fail";
    }

    @Override
    public Member getMemberByID(long id) {
        Member member = memberDao.getMemberByID(id);

        return member;
    }

    public List<Member> getAll(){
        List<Member> members = new ArrayList<>();
        List<Member> memberPOS = memberDao.getAll();

        return members;
    }

    public long count(){
        return memberDao.getExistNum();
    }
}
