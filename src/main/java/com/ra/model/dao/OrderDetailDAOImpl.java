package com.ra.model.dao;

import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.Product;
import com.ra.model.service.ProductService;
import com.ra.utils.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository

public class OrderDetailDAOImpl implements OrderDetailDAO{
    @Autowired
    ProductService productService;
    @Override
    public List<OrderDetail> findAll(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        List<OrderDetail> orderDetailList=new ArrayList<>();
        try {
            CallableStatement callableStatement= connection.prepareCall("CALL PROC_FIND_ALL_ORDER_DETAILS(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                OrderDetail orderDetail=new OrderDetail();
                orderDetail.setOrderId(resultSet.getInt("order_id"));
                Product product=productService.findById(resultSet.getInt("product_id"));
                orderDetail.setProductId(product.getId());
                orderDetail.setQuantity(resultSet.getInt("quantity"));
                orderDetail.setPrice(resultSet.getInt("price"));
                orderDetailList.add(orderDetail);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderDetailList;
    }

    @Override
    public void save(OrderDetail orderDetail) {
        Connection connection=ConnectionDatabase.openConnection();
            try {
                CallableStatement callableStatement = connection.prepareCall("{CALL PROC_CREATE_ORDER_DETAILS(?,?,?,?)}");
                callableStatement.setInt(1, orderDetail.getOrderId());
                callableStatement.setInt(2, orderDetail.getProductId());
                callableStatement.setInt(3, orderDetail.getQuantity());
                callableStatement.setDouble(4, orderDetail.getPrice());
                callableStatement.executeUpdate();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
