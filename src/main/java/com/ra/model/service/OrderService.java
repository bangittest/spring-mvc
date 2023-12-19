package com.ra.model.service;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;

import java.util.List;

public interface OrderService {
    List<Order> findAll();
    void order(Order order);
    void statusOrder(Integer id ,int status);
    Order findById(Integer id);
    List<Order> findALLById(Integer id);
    Order findByIdOrDer(Integer id);
}
