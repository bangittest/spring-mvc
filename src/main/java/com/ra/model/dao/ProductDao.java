package com.ra.model.dao;

import com.ra.model.entity.Category;
import com.ra.model.entity.Product;

import java.util.List;

public interface ProductDao {
    List<Product>findAll();
    int save(Product product);
    boolean update(Product products);
    Product findById(Integer id);
    void delete(Integer id);
    void changeStatus(Integer id);
    boolean findByName(String name);
    List<Product>pagination(Integer noPage, String searchKeyword);
    Integer getTotalPages();
    List<Product> sortProductName(String productName ,String sortDirection);
    List<Product> findByIdList(Integer id);
    void reduceStock(Integer productId, int quantity);

}
