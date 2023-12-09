package com.ra.model.service;

import com.ra.model.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> findAll();
    boolean save(Category category);
    boolean update(Category category);
    Category findById(Integer id);
    void changeStatus(Integer id);
    List<Category>pagination(Integer noPage,String searchKeyword);
    Integer getTotalPages();
}
