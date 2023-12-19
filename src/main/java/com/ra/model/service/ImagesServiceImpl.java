package com.ra.model.service;

import com.ra.model.dao.ImagesDao;
import com.ra.model.entity.Image;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImagesServiceImpl implements ImagesService{
    @Autowired
    ImagesDao imagesDao;
    @Override
    public List<Image> findAll() {
        return imagesDao.findAll();
    }

    @Override
    public boolean save(Image images , int ProductId) {
        return imagesDao.save(images,ProductId);
    }

    @Override
    public boolean update(Image images) {
        return imagesDao.update(images);
    }

    @Override
    public Image findById(Integer id) {
        return imagesDao.findById(id);
    }

    @Override
    public void delete(Integer id) {
        imagesDao.delete(id);
    }

    @Override
    public void deleteForeign(Integer id) {
        imagesDao.deleteForeign(id);
    }

    @Override
    public List<Image> findByProductId(Integer id) {
        return imagesDao.findByProductId(id);
    }
}
