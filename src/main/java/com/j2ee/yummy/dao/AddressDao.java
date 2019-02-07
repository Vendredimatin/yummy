package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.AddressRepository;
import com.j2ee.yummy.model.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

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

    public boolean update(Address address){
        try{
            addressRepository.save(address);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean addAddress(Address address){
        try {
            addressRepository.save(address);
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }

        return true;
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
