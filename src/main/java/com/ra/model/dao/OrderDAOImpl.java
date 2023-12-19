package com.ra.model.dao;

import com.ra.model.entity.Order;
import com.ra.model.service.CustomerService;
import com.ra.model.service.ProductService;
import com.ra.utils.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDAOImpl implements OrderDAO{
    @Autowired
    CustomerService customerService;
    @Autowired
    ProductService productService;
    @Override
    public List<Order> findAll() {
        Connection connection= ConnectionDatabase.openConnection();
        List<Order>orderDAOList=new ArrayList<>();
        try {
            CallableStatement preparedStatement=connection.prepareCall("CALL PROC_FIND_ALL_ORDERS");
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order=new Order();
                order.setId(resultSet.getInt("id"));
                order.setEmail(resultSet.getString("email"));
                order.setFullName(resultSet.getString("full_name"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setNotes(resultSet.getString("notes"));
                order.setDate(resultSet.getDate("order_date"));
                order.setStatus(resultSet.getInt("order_status"));
                order.setTotalPrice(resultSet.getDouble("total"));
                orderDAOList.add(order);

//                OrderDetail orderDetail = new OrderDetail();
//                Product product=productService.findById(resultSet.getInt("product_id"));
//                orderDetail.setProduct(product);
//                orderDetail.setQuantity(resultSet.getInt("quantity"));
//                orderDetail.setPrice(resultSet.getDouble("price"));
//                order.getOrderDetails().add(orderDetail);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderDAOList;
    }

    @Override
    public Integer save(Order order) {
        Connection connection=null;
        try {
            connection=ConnectionDatabase.openConnection();
            CallableStatement callableStatement=connection.prepareCall("{CALL PROC_CREATE_ORDER(?,?,?,?,?,?,?,?)}");
            callableStatement.setInt(1,order.getCustomer().getCustomerId());
            callableStatement.setString(2,order.getEmail());
            callableStatement.setString(3,order.getFullName());
            callableStatement.setString(4,order.getAddress());
            callableStatement.setString(5,order.getPhone());
            callableStatement.setString(6,order.getNotes());
            callableStatement.setDouble(7,order.getTotalPrice());
            callableStatement.setInt(8,Types.INTEGER);
            callableStatement.executeUpdate();
            return callableStatement.getInt(8);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void statusOrder(Integer id, int status) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CHANGE_STATUS_ORDERS_(?,?)");
            callableStatement.setInt(1,id);
            callableStatement.setInt(2,status);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

    }

    @Override
    public Order findById(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        Order order=new Order();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_FIND_BY_ID_ORDERS_(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                order.setId(resultSet.getInt("id"));
                order.setEmail(resultSet.getString("email"));
                order.setFullName(resultSet.getString("full_name"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setNotes(resultSet.getString("notes"));
                order.setDate(resultSet.getDate("order_date"));
                order.setStatus((resultSet.getInt("order_status")));
                order.setTotalPrice(resultSet.getDouble("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return order;
    }

    @Override
    public List<Order> findALLById(Integer id) {
        Connection connection= ConnectionDatabase.openConnection();
        List<Order>orderDAOList=new ArrayList<>();
        try {
            CallableStatement preparedStatement=connection.prepareCall("CALL PROC_FIND_ALL_ORDERS_BY_ID(?)");
            preparedStatement.setInt(1,id);
            ResultSet resultSet=preparedStatement.executeQuery();
            while (resultSet.next()) {
                Order order=new Order();
                order.setId(resultSet.getInt("id"));
                order.setEmail(resultSet.getString("email"));
                order.setFullName(resultSet.getString("full_name"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setNotes(resultSet.getString("notes"));
                order.setDate(resultSet.getDate("order_date"));
                order.setStatus(resultSet.getInt("order_status"));
                order.setTotalPrice(resultSet.getDouble("total"));
                orderDAOList.add(order);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return orderDAOList;
    }

    @Override
    public Order findByIdOrDer(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        Order order=new Order();
        try {
            CallableStatement callableStatement= connection.prepareCall("CALL PROC_FIND_ALL_ORDERS_BY_ID_ORDERS(?)");
            callableStatement.setInt("1",id);
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){

                order.setId(resultSet.getInt("id"));
                order.setEmail(resultSet.getString("email"));
                order.setFullName(resultSet.getString("full_name"));
                order.setAddress(resultSet.getString("address"));
                order.setPhone(resultSet.getString("phone"));
                order.setNotes(resultSet.getString("notes"));
                order.setDate(resultSet.getDate("order_date"));
                order.setStatus(resultSet.getInt("order_status"));
                order.setTotalPrice(resultSet.getDouble("total"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return order;
    }
}
