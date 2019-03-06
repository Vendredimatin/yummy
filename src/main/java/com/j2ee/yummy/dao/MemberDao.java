package com.j2ee.yummy.dao;

import com.j2ee.yummy.PO.MemberPO;
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

    public List<MemberPO> getAll(){
        return memberRepository.findAll();
    }

    public long getExistNum(){
        return memberRepository.count();
    }

    public MemberPO login(String email,String password){
        MemberPO member = memberRepository.findByEmailAndPassword(email,password);
        assert member!=null:"member 为空 ！";

        return member;
    }

    public Boolean update(MemberPO member){
        try {
            memberRepository.save(member);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Boolean insert(MemberPO member){
        try{
            memberRepository.save(member);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public MemberPO getMemberByID(long id){
        MemberPO memberPO = memberRepository.getOne(id);

        return memberPO;
    }


}
