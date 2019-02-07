package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.CancelledMemberDao;
import com.j2ee.yummy.model.CancelledMember;
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
    CancelledMemberDao cancelledMemberDao;

    @Override
    public boolean add(CancelledMember cancelledMember) {
        return cancelledMemberDao.add(cancelledMember);
    }

    @Override
    public boolean isCancelled(long id) {
        return cancelledMemberDao.isExist(id);
    }
}
