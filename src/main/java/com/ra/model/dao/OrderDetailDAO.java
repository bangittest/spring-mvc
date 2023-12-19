package com.ra.model.dao;

import com.ra.model.entity.OrderDetail;

import java.util.List;

public interface OrderDetailDAO {
    List<OrderDetail> findAll(Integer id);
    void save(OrderDetail orderDetail);
}
