package com.ra.model.service;

import com.ra.model.dao.OrderDetailDAO;
import com.ra.model.entity.OrderDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    OrderDetailDAO orderDetailDAO;
    @Override
    public List<OrderDetail> findAll(Integer id) {
        return orderDetailDAO.findAll(id);
    }
}
