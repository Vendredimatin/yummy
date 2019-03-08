package com.j2ee.yummy.serviceImpl;


import com.j2ee.yummy.dao.BalanceDao;
import com.j2ee.yummy.dao.MemberDao;
import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.model.Member;
import com.j2ee.yummy.service.MemberService;
import com.j2ee.yummy.yummyEnum.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    BalanceDao balanceDao;

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
    @Transactional
    public String register(String email, String password, String name, String phone) {
        Member member = new Member();
        member.setEmail(email);
        member.setPassword(password);
        member.setName(name);
        member.setPhone(phone);
        member = memberDao.insert(member);

        //还要在银行中注册这个账户
        Balance balance = new Balance();
        balance.setUserID(member.getId());
        balance.setPassword(member.getPassword());
        balance.setBalance(1000);
        balance.setProfit(0);
        balance.setCost(0);
        balance.setUserType(UserType.Member);
        balanceDao.insert(balance);
        return "success";
    }

    @Override
    public Member getMemberByID(long id) {
        Member member = memberDao.getMemberByID(id);

        return member;
    }

    public List<Member> getAll(){
        List<Member> memberPOS = memberDao.getAll();

        return memberPOS;
    }

    public long count(){
        return memberDao.getExistNum();
    }

    public String sendEmail(String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("2860957268@qq.com");
        message.setTo(email);
        message.setSubject("主题：注册申请");
        String identifyCode = getIdentifyCode();
        message.setText(identifyCode);

        javaMailSender.send(message);

        return identifyCode;
    }

    public String getIdentifyCode(){
        String str="ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder sb=new StringBuilder(4);
        for(int i=0;i<4;i++)
        {
            char ch=str.charAt(new Random().nextInt(str.length()));
            sb.append(ch);
        }

        return sb.toString();

    }

    public boolean isExists(String email){
        return memberDao.isExists(email);
    }
}
