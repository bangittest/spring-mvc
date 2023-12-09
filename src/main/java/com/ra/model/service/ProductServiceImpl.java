package com.ra.model.service;

import com.ra.model.dao.ProductDao;
import com.ra.model.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductDao productDao;
    @Override
    public List<Products> findAll() {
        return productDao.findAll();
    }

    @Override
    public boolean save(Products products) {
        return productDao.save(products);
    }

    @Override
    public boolean update(Products products) {
        return productDao.update(products);
    }

    @Override
    public Products findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    public void delete(Integer id) {
        productDao.delete(id);
    }
}
