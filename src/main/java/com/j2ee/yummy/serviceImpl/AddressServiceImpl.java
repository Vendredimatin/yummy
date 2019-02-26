package com.j2ee.yummy.serviceImpl;

import com.j2ee.yummy.dao.AddressDao;
import com.j2ee.yummy.model.Address;
import com.j2ee.yummy.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: yummy
 * @description: addressService的实现类
 * @author: Liu Hanyi
 * @create: 2019-02-07 20:01
 **/

@Service
public class AddressServiceImpl implements AddressService {
    @Autowired
    AddressDao addressDao;

    @Override
    public Address getAddressByID(long id) {
        return addressDao.getAddressByID(id);
    }

    @Override
    public boolean update(Address address) {
        return addressDao.update(address);
    }

    @Override
    public Address add(Address address) {
        return addressDao.addAddress(address);
    }

    @Override
    public boolean delete(long id) {
        return addressDao.deleteAddress(id);
    }

    @Override
    public List<Address> getAddressesByMemberID(long memberID) {
        return addressDao.getAddressesByMemID(memberID);
    }
}
