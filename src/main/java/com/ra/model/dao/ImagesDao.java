package com.ra.model.dao;

import com.ra.model.entity.Image;

import java.util.List;

public interface ImagesDao {
    List<Image> findAll();
    boolean save(Image images, int ProductId);
    boolean update(Image images);
    Image findById(Integer id);

    void delete(Integer id);
    void deleteForeign(Integer id);

    List<Image> findByProductId(Integer id);
}
