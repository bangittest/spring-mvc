package com.ra.model.dao;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;

import java.util.List;

public interface OrderDAO {
    List<Order> findAll();
    Integer save(Order order);
    void statusOrder(Integer id ,int status);
    Order findById(Integer id);
    List<Order> findALLById(Integer id);
   Order findByIdOrDer(Integer id);
}
