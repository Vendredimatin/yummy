package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long> {

    public List<Order> findAllByMemberID(long memberID);
}
