package com.j2ee.yummy.Repository;

import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order,Long>, JpaSpecificationExecutor {

    public List<Order> findAllByMemberID(long memberID);

    public List<Order> findAllByCanteenID(long canteenID);

    @Transactional
    @Modifying
    @Query("update Order o set o.orderState = ?1 where o.id = ?2")
    public int updateOrderState(OrderState orderState,long orderID);

    public List<Order> findAllByMemberIDAndTimeBetweenAndTotalPriceBetweenAndCanteenNameLike(long memberID, LocalDate startTime,LocalDate endTime, double minPrice, double maxPrice,String canteenName);

    public List<Order> findAllByMemberIDAndTimeBetween(long memberID, LocalDateTime startTime, LocalDateTime endTime);
}
