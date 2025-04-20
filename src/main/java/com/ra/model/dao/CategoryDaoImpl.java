package com.ra.model.dao;

import com.ra.model.entity.Category;
import com.ra.utils.ConnectionDatabase;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CategoryDaoImpl implements CategoryDao {
    @Override
    public List<Category> findAll() {
        List<Category> categoryList = new ArrayList<>();
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_ALL_CATEGORY");
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setStatus(rs.getBoolean("status"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categoryList;
    }

    @Override
    public boolean save(Category category) {
        Connection connection = null;
        try {
            connection = ConnectionDatabase.openConnection();
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_CREATE_CATEGORY(?,?)");
            callableStatement.setString(1, category.getName());
            callableStatement.setBoolean(2, category.isStatus());
            int check = callableStatement.executeUpdate();
            if (check > 0) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public boolean update(Category category) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_UPDATE_CATEGORY(?,?,?)");
            callableStatement.setInt(1, category.getId());
            callableStatement.setString(2, category.getName());
            callableStatement.setBoolean(3, category.isStatus());
            int check = callableStatement.executeUpdate();
            if (check > 0) {
                return true;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return false;
    }

    @Override
    public Category findById(Integer id) {
        Connection connection = ConnectionDatabase.openConnection();
        Category category = new Category();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_BY_ID_CATEGORY(?)");
            callableStatement.setInt(1, id);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setStatus(rs.getBoolean("status"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return category;
    }

    @Override
    public void changeStatus(Integer id) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_CHANGE_STATUS_CATEGORY(?) ");
            callableStatement.setInt(1, id);
            callableStatement.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }


    private int LIMIT = 6;
    private int totalPage = 0;

    @Override
    public List<Category> pagination(Integer noPage, String searchKeyword) {


        List<Category> categoryList = new ArrayList<>();
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_PAGINATION_SEACH_CATEGORY(?,?,?,?)");
            callableStatement.setInt(1, LIMIT);
            callableStatement.setInt(2, noPage);
            callableStatement.setString(3, searchKeyword);
            callableStatement.setInt(4, Types.INTEGER);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setStatus(rs.getBoolean("status"));
                categoryList.add(category);
            }
            this.totalPage = callableStatement.getInt(4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categoryList;
    }

    @Override
    public Integer getTotalPages() {
        return totalPage;
    }

    @Override
        public List<Category> sortCategoryName (String categoryName,String sortDirection){
        List<Category> categoryList = new ArrayList<>();
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_ORDER_BY_CATEGORY(?,?)");
            callableStatement.setString(1, categoryName);
            callableStatement.setString(2, sortDirection);
            ResultSet rs = callableStatement.executeQuery();
            while (rs.next()) {
                Category category = new Category();
                category.setId(rs.getInt("id"));
                category.setName(rs.getString("name"));
                category.setStatus(rs.getBoolean("status"));
                categoryList.add(category);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return categoryList;
    }

    @Override
    public boolean findByName(String name) {
        Connection connection = ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement = connection.prepareCall("CALL PROC_FIND_BY_NAME_CATEGORY(?)");
            callableStatement.setString(1, name);
            ResultSet rs = callableStatement.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }
}

