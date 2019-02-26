package com.j2ee.yummy.service;

import com.j2ee.yummy.model.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    public Address getAddressByID(long id);

    public boolean update(Address address);

    public Address add(Address address);

    public boolean delete(long id);

    public List<Address> getAddressesByMemberID(long memberID);
}
