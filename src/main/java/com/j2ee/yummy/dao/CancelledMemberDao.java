package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.CancelledMemberRepository;
import com.j2ee.yummy.model.CancelledMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @program: yummy
 * @description: 已注销会员的dao类
 * @author: Liu Hanyi
 * @create: 2019-02-07 20:11
 **/
@Repository
public class CancelledMemberDao {

    @Autowired
    CancelledMemberRepository cancelledMemberRepository;

    public boolean add(CancelledMember cancelledMember){
        try {
            cancelledMemberRepository.save(cancelledMember);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean isExist(long id){
        return cancelledMemberRepository.existsById(id);
    }
}
