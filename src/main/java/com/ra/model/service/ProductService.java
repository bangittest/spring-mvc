package com.ra.model.service;

import com.ra.model.dto.product.ProductDto;
import com.ra.model.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> findAll();
    int save(ProductDto productDto);
    boolean update(Product product);
    Product findById(Integer id);
    void delete(Integer id);
    boolean findByName(String name);

    List<Product>pagination(Integer noPage, String searchKeyword);
    Integer getTotalPages();
    List<Product> sortProductName(String productName ,String sortDirection);
    List<Product> findByIdList(Integer id);
    void reduceStock(Integer productId, int quantity);
    void changeStatus(Integer id);
}
