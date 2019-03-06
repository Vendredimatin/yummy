package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.MemberRepository;
import com.j2ee.yummy.model.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: yummy
 * @description: 会员类的数据逻辑类
 * @author: Liu Hanyi
 * @create: 2019-02-04 10:36
 **/

@Repository
public class MemberDao {
    @Autowired
    MemberRepository memberRepository;

    public List<Member> getAll(){
        return memberRepository.findAll();
    }

    public long getExistNum(){
        return memberRepository.count();
    }

    public Member login(String email,String password){
        Member member = memberRepository.findByEmailAndPassword(email,password);
        assert member!=null:"member 为空 ！";

        return member;
    }

    public Boolean update(Member member){
        try {
            memberRepository.save(member);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean insert(Member member){
        try{
            memberRepository.save(member);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Member getMemberByID(long id){
        Member memberPO = memberRepository.getOne(id);

        return memberPO;
    }


}
