package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}
