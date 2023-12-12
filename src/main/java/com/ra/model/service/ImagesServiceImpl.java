package com.ra.model.service;

import com.ra.model.dao.ImagesDao;
import com.ra.model.entity.Images;
import com.ra.model.entity.Products;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService{
    @Autowired
    ImagesDao imagesDao;
    @Override
    public List<Images> findAll() {
        return imagesDao.findAll();
    }

    @Override
    public boolean save(Images images , int ProductId) {
        return imagesDao.save(images,ProductId);
    }

    @Override
    public boolean update(Images images) {
        return imagesDao.update(images);
    }

    @Override
    public Images findById(Integer id) {
        return imagesDao.findById(id);
    }

    @Override
    public void delete(Integer id) {
        imagesDao.findById(id);
    }

    @Override
    public void deleteForeign(Integer id) {
        imagesDao.deleteForeign(id);
    }

    @Override
    public List<Images> findByProductId(Integer id) {
        return imagesDao.findByProductId(id);
    }
}
