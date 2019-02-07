package com.j2ee.yummy.service;

import com.j2ee.yummy.model.Address;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {

    public Address getAddressByID(long id);

    public boolean update(Address address);

    public boolean add(Address address);

    public boolean delete(long id);
}
