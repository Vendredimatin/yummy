package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.AddressRepository;
import com.j2ee.yummy.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @program: yummy
 * @description: address的dao类
 * @author: Liu Hanyi
 * @create: 2019-02-07 19:56
 **/

@Repository
public class AddressDao {
    @Autowired
    AddressRepository addressRepository;

    public Address getAddressByID(long id){
        return addressRepository.getOne(id);
    }

    public List<Address> getAddressesByMemID(long memberID){
        return addressRepository.findAddressesByMemberID(memberID);
    }

    public boolean update(Address address){
        try{
            addressRepository.saveAndFlush(address);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public Address addAddress(Address address){
        return addressRepository.save(address);
    }

    public boolean deleteAddress(long id){
        try {
            addressRepository.deleteById(id);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

}
