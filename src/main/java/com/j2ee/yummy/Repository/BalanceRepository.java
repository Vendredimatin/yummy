package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.Balance;
import com.j2ee.yummy.yummyEnum.UserType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    public Balance findByUserIDAndUserType(long userID, UserType userType);

    public Balance findBalanceByUserID(long userID);

    public List<Balance> findAllByUserType(UserType userType);
}
