package com.ra.model.dao;

import com.ra.model.entity.Images;
import com.ra.model.entity.Products;
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
public class ImagesImpl implements ImagesDao{
    @Autowired
    ProductService productService;

    @Override
    public List<Images> findAll() {
        Connection connection= ConnectionDatabase.openConnection();
        List<Images>imagesList=new ArrayList<>();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_FIND_ALL_IMAGE");
            ResultSet resultSet= callableStatement.executeQuery();
            while (resultSet.next()){
                Images images=new Images();
                images.setImageUrl(resultSet.getString("url"));
                images.setProductId(resultSet.getInt("product_id"));
                imagesList.add(images);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
        return imagesList;
    }

    @Override
    public boolean save(Images images,int ProductId) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_CREATE_IMAGE(?,?)");
            callableStatement.setString(1,images.getImageUrl());
            callableStatement.setInt(2,ProductId);
            int check=callableStatement.executeUpdate();
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
    public boolean update(Images images) {
        return false;
    }

    @Override
    public Images findById(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        Images images=new Images();
        try {
            CallableStatement callableStatement= connection.prepareCall("CALL PROC_FIND_BY_ID_IMAGE(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()) {
                images.setId(resultSet.getInt("id"));
                images.setImageUrl(resultSet.getString("url"));
                images.setProductId(resultSet.getInt("product_id"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return images;
    }

    @Override
    public void delete(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_DELETE_IMAGE(?)");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public void deleteForeign(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
        try {
            CallableStatement callableStatement=connection.prepareCall("CALL PROC_DELETE_FOREIGN_IMAGE(?)");
            callableStatement.setInt(1,id);
            callableStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }
    }

    @Override
    public List<Images> findByProductId(Integer id) {
        Connection connection=ConnectionDatabase.openConnection();
       List<Images> imagesList=new ArrayList<>();
        try {
            CallableStatement callableStatement= connection.prepareCall("CALL PROC_FIND_BY_PRODUCT_ID_IMAGE(?)");
            callableStatement.setInt(1,id);
            ResultSet resultSet=callableStatement.executeQuery();
            while (resultSet.next()) {
                Images images=new Images();
                images.setId(resultSet.getInt("id"));
                images.setImageUrl(resultSet.getString("url"));
                images.setProductId(resultSet.getInt("product_id"));
                imagesList.add(images);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }finally {
            ConnectionDatabase.closeConnection(connection);
        }

        return imagesList;
    }
}
