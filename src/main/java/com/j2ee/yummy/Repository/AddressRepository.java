package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address,Long> {
}
