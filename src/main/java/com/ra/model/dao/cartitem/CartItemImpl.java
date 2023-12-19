package com.ra.model.dao.cartitem;

import com.ra.model.entity.CartItem;
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

public class CartItemImpl implements CartItemDao{
    @Autowired
    ProductService productService;
    @Override
    public List<CartItem> getCarItemByCartId(Integer cartId) {
        List<CartItem>cartItems=new ArrayList<>();
        Connection connection= ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_GET_CART_ITEMS_BY_CART_ID(?)");
            callableStatement.setInt(1,cartId);
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                CartItem cartItem=new CartItem();
                cartItem.setCartId(resultSet.getInt("cart_id"));
                Product product=productService.findById(resultSet.getInt("product_id"));
                cartItem.setProduct(product);
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return cartItems;
    }

    @Override
    public void addToCart(CartItem cartItem) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CREATE_CART_ITEM(?,?,?)");
            callableStatement.setInt(1,cartItem.getCartId());
            callableStatement.setInt(2,cartItem.getProduct().getProductId());
            callableStatement.setInt(3,cartItem.getQuantity());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public CartItem getCartByCartIdProId( Integer cartId, Integer productId) {
        Connection connection=ConnectionDatabase.openConnection();
        CartItem cartItem=null;
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_GET_CART_ITEMS_BY_CART_ID_AND_PRODUCT_ID(?,?)");
            callableStatement.setInt(1,cartId);
            callableStatement.setInt(2,productId);
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                cartItem = new CartItem();
                cartItem.setCartId(resultSet.getInt("cart_id"));
                Product product=productService.findById(resultSet.getInt("product_id"));
                cartItem.setProduct(product);
                cartItem.setQuantity(resultSet.getInt("quantity"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return cartItem;
    }

    @Override
    public void update(Integer cartId,Integer productId ,CartItem cartItem) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_UPDATE_CART_ITEM(?,?,?)");
            callableStatement.setInt(1,cartId);
            callableStatement.setInt(2,productId);
            callableStatement.setInt(3,cartItem.getQuantity());
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void delete(Integer productId) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_DELETE_CART_ITEM(?)");
            callableStatement.setInt(1,productId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<CartItem> findAll() {
        List<CartItem>cartItems=new ArrayList<>();
        Connection connection= ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_FIND_ALL_CART_ITEM");
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                CartItem cartItem=new CartItem();
                cartItem.setCartId(resultSet.getInt("cart_id"));
                Product product=productService.findById(resultSet.getInt("product_id"));
                cartItem.setProduct(product);
                cartItem.setQuantity(resultSet.getInt("quantity"));
                cartItems.add(cartItem);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return cartItems;
    }

    @Override
    public void deleteCartId(Integer cartId) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_DELETE_CART_ITEMS_ID(?)");
            callableStatement.setInt(1,cartId);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public CartItem findCartItemByProductId(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        CartItem cartItem=null;
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_GET_CART_ITEMS_BY_PRODUCT_CART_ID(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                cartItem = new CartItem();
                cartItem.setCartId(resultSet.getInt("cart_id"));
                Product product=productService.findById(resultSet.getInt("product_id"));
                cartItem.setProduct(product);
                cartItem.setQuantity(resultSet.getInt("quantity"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return cartItem;
    }
}
