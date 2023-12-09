package com.ra.model.dao;

import com.ra.model.entity.Category;
import com.ra.model.entity.Products;
import com.ra.model.service.CategoryService;
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
public class ProductDaoImpl implements ProductDao{
    @Autowired
    CategoryService categoryService;
    @Override
    public List<Products> findAll() {
        List<Products>productsList=new ArrayList<>();
        Connection connection= ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("Call PROC_FIND_ALL_PRODUCT");
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()) {
                Products products=new Products();
                products.setProductId(resultSet.getInt("id"));
                Category category=categoryService.findById(resultSet.getInt("category_id"));
                products.setCategory(category);
                products.setProductName(resultSet.getString("name"));
                products.setDescription(resultSet.getString("description"));
                products.setPrice(resultSet.getDouble("price"));
                products.setQuantity(resultSet.getInt("quantity"));
                products.setImageUrl(resultSet.getString("url_image"));
                products.setProductStatus(resultSet.getBoolean("status"));
                productsList.add(products);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return productsList;
    }

    @Override
    public boolean save(Products products) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CREATE_PRODUCT(?,?,?,?,?,?)");
            callableStatement.setInt(1,products.getCategory().getCategoryId());
            callableStatement.setString(2,products.getProductName());
            callableStatement.setString(3,products.getDescription());
            callableStatement.setDouble(4,products.getPrice());
            callableStatement.setString(5,products.getImageUrl());
            callableStatement.setInt(6,products.getQuantity());
            int check= callableStatement.executeUpdate();
            if (check>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return false;
    }

    @Override
    public boolean update(Products products) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_UPDATE_PRODUCT(?,?,?,?,?,?,?,?)");
            callableStatement.setInt(1,products.getCategory().getCategoryId());
            callableStatement.setString(2,products.getProductName());
            callableStatement.setString(3,products.getDescription());
            callableStatement.setDouble(4,products.getPrice());
            callableStatement.setInt(5,products.getQuantity());
            callableStatement.setBoolean(6,products.getProductStatus());
            callableStatement.setString(7,products.getImageUrl());
            callableStatement.setInt(8,products.getProductId());
            int check= callableStatement.executeUpdate();
            if (check>0){
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Products findById(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        Products products=new Products();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_FIND_BY_ID_PRODUCT(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()){
                products.setProductId(resultSet.getInt("id"));
                Category category=categoryService.findById(resultSet.getInt("category_id"));
                products.setCategory(category);
                products.setProductName(resultSet.getString("name"));
                products.setDescription(resultSet.getString("description"));
                products.setPrice(resultSet.getDouble("price"));
                products.setQuantity(resultSet.getInt("quantity"));
                products.setImageUrl(resultSet.getString("url_image"));
                products.setProductStatus(resultSet.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return products;
    }

    @Override
    public void delete(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("call PROC_DELETE_PRODUCT(?)");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}
