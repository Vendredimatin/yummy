package com.j2ee.yummy.dao;

import com.j2ee.yummy.Repository.OrderRepository;
import com.j2ee.yummy.model.order.Order;
import com.j2ee.yummy.model.order.stateDesignPattern.OrderState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: yummy
 * @description: order的dao类
 * @author: Liu Hanyi
 * @create: 2019-03-01 23:01
 **/
@Repository
public class OrderDao {
    @Autowired
    OrderRepository orderRepository;

    private static final double NULL_PRICE = -1;
    private static final String NULL_CANTEENAME = "null";
    private static final String ALL_STATE = "所有";


    public Order insert(Order order){
        return orderRepository.save(order);
    }

    public List<Order> getOrdersByMemID(long memberID){
        return orderRepository.findAllByMemberID(memberID);
    }

    public List<Order> getOrdersByCanID(long canteenID){
        return orderRepository.findAllByCanteenID(canteenID);
    }

    public boolean updateOrderState(OrderState orderState,long orderID){
        orderRepository.updateOrderState(orderState,orderID);
        return true;
    }

    public Order getOrderByID(long orderID){
        return orderRepository.getOne(orderID);
    }

    public void update(Order order){
        orderRepository.saveAndFlush(order);
    }

    public Page<Order> findByConditions(long memberID, LocalDate startTime, LocalDate endTime, double maxPrice, double minPrice, String canteenName, String orderState, Pageable pageable){
        Page<Order> orders = null;

        Specification querySpecifi = new Specification() {
            @Override
            public Predicate toPredicate(Root root, CriteriaQuery criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                predicates.add(criteriaBuilder.equal(root.get("memberID"),memberID));
                if (null != startTime){
                    LocalDateTime stime = LocalDateTime.of(startTime.getYear(),startTime.getMonth(),startTime.getDayOfMonth(),0,0);
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("time"),stime));
                }
                if (null != endTime) {
                    LocalDateTime etime = LocalDateTime.of(endTime.getYear(),endTime.getMonth(),endTime.getDayOfMonth(),0,0);
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("time"),etime));
                }
                if (NULL_PRICE != minPrice)
                    predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("totalPrice"),minPrice));
                if (NULL_PRICE != maxPrice)
                    predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("totalPrice"),maxPrice));
                if (!canteenName.equals(NULL_CANTEENAME))
                    predicates.add(criteriaBuilder.equal(root.get("canteenName"),canteenName));
                if (!orderState.equals(ALL_STATE))
                    predicates.add(criteriaBuilder.equal(root.get("orderState"),OrderState.valueOf(orderState)));
                return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
            }
        };

        orders = orderRepository.findAll(querySpecifi,pageable);

        return orders;
    }
}
