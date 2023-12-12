package com.ra.model.dao;

import com.ra.model.entity.Products;

import java.util.List;

public interface ProductDao {
    List<Products>findAll();
    int save(Products products);
    boolean update(Products products);
    Products findById(Integer id);
    void delete(Integer id);
}
