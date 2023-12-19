package com.ra.model.service;

import com.ra.model.dao.CategoryDao;
import com.ra.model.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    CategoryDao categoryDao;
    @Override
    public List<Category> findAll() {
        return categoryDao.findAll();
    }

    @Override
    public boolean save(Category category) {
        return categoryDao.save(category);
    }

    @Override
    public boolean update(Category category) {
        return categoryDao.update(category);
    }

    @Override
    public Category findById(Integer id) {
        return categoryDao.findById(id);
    }

    @Override
    public void changeStatus(Integer id) {
        categoryDao.changeStatus(id);
    }

    @Override
    public List<Category> pagination(Integer noPage,String searchKeyword) {
        return categoryDao.pagination(noPage, searchKeyword);
    }
    @Override
    public Integer getTotalPages() {
        return categoryDao.getTotalPages();
    }

    @Override
    public List<Category> sortCategoryName(String categoryName,String sort) {
        return categoryDao.sortCategoryName(categoryName,sort);
    }

    @Override
    public boolean findByName(String name) {
        return categoryDao.findByName(name);
    }
}
