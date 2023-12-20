package com.ra.model.dao;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.model.service.CategoryService;
import com.ra.utils.ConnectionDatabase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDaoImpl implements ProductDao{
    @Autowired
    CategoryService categoryService;
    @Override
    public List<Product> findAll() {
        List<Product>productsList=new ArrayList<>();
        Connection connection= ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("Call PROC_FIND_ALL_PRODUCT");
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()) {
                Product products=new Product();
                products.setProductId(resultSet.getInt("id"));
                Category category=categoryService.findById(resultSet.getInt("category_id"));
                products.setCategory(category);
                products.setProductName(resultSet.getString("name"));
                products.setDescription(resultSet.getString("description"));
                products.setPrice(resultSet.getDouble("price"));
                products.setStock(resultSet.getInt("stock"));
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
    public int save(Product products) {
        Connection connection=null;
        CallableStatement callableStatement=null;
        try {
            connection=ConnectionDatabase.openConnection();
            callableStatement=connection.prepareCall("CALL PROC_CREATE_PRODUCT(?,?,?,?,?,?,?)");
            callableStatement.setInt(1,products.getCategory().getCategoryId());
            callableStatement.setString(2,products.getProductName());
            callableStatement.setString(3,products.getDescription());
            callableStatement.setDouble(4,products.getPrice());
            callableStatement.setString(5,products.getImageUrl());
            callableStatement.setInt(6,products.getStock());
            callableStatement.registerOutParameter(7, Types.INTEGER);
             callableStatement.executeUpdate();

            return callableStatement.getInt(7);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

    }

    @Override
    public boolean update(Product products) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_UPDATE_PRODUCT(?,?,?,?,?,?,?,?)");
            callableStatement.setInt(1,products.getCategory().getCategoryId());
            callableStatement.setString(2,products.getProductName());
            callableStatement.setString(3,products.getDescription());
            callableStatement.setDouble(4,products.getPrice());
            callableStatement.setInt(5,products.getStock());
            callableStatement.setString(6,products.getImageUrl());
            callableStatement.setBoolean(7,products.getProductStatus());
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
    public Product findById(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        Product products=new Product();
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
                products.setStock(resultSet.getInt("stock"));
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

    @Override
    public void changeStatus(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("call PROC_CHANGE_STATUS_PRODUCT(?)");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public boolean findByName(String name) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement= connection.prepareCall("CALL PROC_FIND_BY_NAME_PRODUCT(?)");
            callableStatement.setString(1,name);
            ResultSet rs= callableStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static final int LIMIT = 6;
    private int totalPages = 0;

    @Override
    public List<Product> pagination(Integer noPage, String searchKeyword) {
        List<Product> productList = new ArrayList<>();
        try (Connection connection = ConnectionDatabase.openConnection()) {
            try (CallableStatement callableStatement = connection.prepareCall("CALL PROC_PAGINATION_SEACH_PRODUCT(?,?,?,?)")) {
                callableStatement.setInt(1, LIMIT);
                callableStatement.setInt(2, noPage);
                callableStatement.setString(3, searchKeyword);
                callableStatement.registerOutParameter(4, Types.INTEGER);

                try (ResultSet resultSet = callableStatement.executeQuery()) {
                    while (resultSet.next()) {
                        Product product = new Product();
                        product.setProductId(resultSet.getInt("id"));
                        Category category = categoryService.findById(resultSet.getInt("category_id"));
                        product.setCategory(category);
                        product.setProductName(resultSet.getString("name"));
                        product.setDescription(resultSet.getString("description"));
                        product.setPrice(resultSet.getDouble("price"));
                        product.setStock(resultSet.getInt("stock"));
                        product.setImageUrl(resultSet.getString("url_image"));
                        product.setProductStatus(resultSet.getBoolean("status"));
                        productList.add(product);
                    }
                }
                this.totalPages = callableStatement.getInt(4);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return productList;
    }


    @Override
    public Integer getTotalPages() {
        return totalPages;
    }

    @Override
    public List<Product> sortProductName(String productName, String sortDirection) {
        List<Product> productList = new ArrayList<>();
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_ORDER_BY_PRODUCT_ALL(?,?)");
            callableStatement.setString(1, productName);
            callableStatement.setString(2, sortDirection);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("id"));
                Category category=categoryService.findById(rs.getInt("category_id"));
                product.setCategory(category);
                product.setProductName(rs.getString("name"));
                product.setDescription(rs.getString("description"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("url_image"));
                product.setStock(rs.getInt("stock"));
                product.setProductStatus(rs.getBoolean("status"));
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return productList;
    }

    @Override
    public List<Product> findByIdList(Integer id) {
        List<Product> productList = new ArrayList<>();
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_CATEGORY_ID_CATEGORY(?)");
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getInt("id"));
                Category category=categoryService.findById(rs.getInt("category_id"));
                product.setCategory(category);
                product.setProductName(rs.getString("name"));
                product.setPrice(rs.getDouble("price"));
                product.setImageUrl(rs.getString("url_image"));
                product.setStock(rs.getInt("stock"));
                product.setProductStatus(rs.getBoolean("status"));
                productList.add(product);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return productList;
    }

    @Override
    public void reduceStock(Integer productId, int quantity) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_UPDATE_STOCK_PRODUCT(?,?)");
            callableStatement.setInt(1,productId);
            callableStatement.setInt(2,quantity);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

}
