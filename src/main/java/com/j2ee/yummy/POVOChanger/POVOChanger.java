package com.j2ee.yummy.POVOChanger;

import com.alibaba.fastjson.JSON;
import com.j2ee.yummy.PO.MemberPO;
import com.j2ee.yummy.model.Member;

/**
 * @program: yummy
 * @description: POVOChanger类
 * @author: Liu Hanyi
 * @create: 2019-02-06 10:13
 **/

public class POVOChanger {

    public static MemberPO toMemberPO(Member member){
        MemberPO memberPO = new MemberPO();
        memberPO.setId(member.getId());
        memberPO.setName(member.getName());
        memberPO.setPassword(member.getPassword());
        memberPO.setEmail(member.getEmail());
        memberPO.setAddresses(JSON.toJSONString(member.getAddresses()));
        memberPO.setMemberLevel(member.getMemberLevel());
        memberPO.setPhone(member.getPhone());
        memberPO.setProfile(member.getProfile());
        return memberPO;
    }

    public static Member toMember(MemberPO memberPO){
        Member member = new Member();
        member.setId(memberPO.getId());
        member.setName(memberPO.getName());
        member.setPassword(memberPO.getPassword());
        member.setEmail(memberPO.getEmail());
        //memberPO.setAddresses(JSON.toJSONString(member.getAddresses()));
        member.setMemberLevel(memberPO.getMemberLevel());
        member.setPhone(memberPO.getPhone());
        member.setProfile(memberPO.getProfile());

        return member;
    }
}
