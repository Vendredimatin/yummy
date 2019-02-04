package com.j2ee.yummy.serviceImpl;

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
    public boolean login(String email, String password) {
        Member member = memberDao.login(email,password);
        return Objects.isNull(member);
    }

    @Override
    public boolean update(Member member) {
        return memberDao.update(member);
    }

    @Override
    public boolean register(Member member) {
        return memberDao.insert(member);
    }
}
