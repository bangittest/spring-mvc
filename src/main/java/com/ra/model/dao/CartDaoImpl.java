package com.ra.model.dao;

import com.ra.model.entity.Cart;
import com.ra.utils.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.*;
@Repository
public class CartDaoImpl implements CartDao{
    @Override
    public Cart findAllCartCustomer(Integer id) {
        Connection connection = ConnectionDatabase.openConnection();
        Cart cart = null;
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_BY_ID_CUSTOMER_CART(?)");
            callableStatement.setInt(1, id);

            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                cart = new Cart();
                cart.setCartId(resultSet.getInt("id"));
                cart.setCustomerId(resultSet.getInt("customer_id"));
            }

            callableStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return cart;
    }

    @Override
    public Integer createCart(Integer customerId) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_CREATE_CART(?, ?)");
            callableStatement.setInt(1, customerId);
            callableStatement.registerOutParameter(2, Types.INTEGER);
            callableStatement.execute();
            Integer cartId = callableStatement.getInt(2);
            callableStatement.close();
            return cartId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public Cart getCartByCustomerId(Integer customerId) {
        Connection connection = ConnectionDatabase.openConnection();
        Cart cart = null;
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_BY_ID_CUSTOMER_CART(?)");
            callableStatement.setInt(1, customerId);

            ResultSet resultSet = callableStatement.executeQuery();

            if (resultSet.next()) {
                cart = new Cart();
                cart.setCartId(resultSet.getInt("id"));
                cart.setCustomerId(resultSet.getInt("customer_id"));
            }

            callableStatement.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return cart;
    }
}
