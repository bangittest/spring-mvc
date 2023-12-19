package com.ra.model.service;

import com.ra.model.dao.ProductDao;
import com.ra.model.dto.product.ProductDto;
import com.ra.model.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService{
    @Autowired
    ProductDao productDao;
    @Override
    public List<Product> findAll() {
        return productDao.findAll();
    }

    @Override
    public int save(ProductDto productDto) {
            Product product=new Product();
            product.setProductId(productDto.getProductId());
            product.setProductName(productDto.getProductName());
            product.setPrice(productDto.getPrice());
            product.setDescription(productDto.getDescription());
            product.setCategory(productDto.getCategory());
            product.setProductStatus(productDto.isProductStatus());
            product.setImageUrl(productDto.getFile1().getOriginalFilename());
            product.setStock(productDto.getStock());
        return productDao.save(product);
    }

    @Override
    public boolean update(Product product) {

        return productDao.update(product);
    }

    @Override
    public Product findById(Integer id) {
        return productDao.findById(id);
    }

    @Override
    public void delete(Integer id) {
        productDao.delete(id);
    }

    @Override
    public boolean findByName(String name) {
        return productDao.findByName(name);
    }

    @Override
    public List<Product> pagination(Integer noPage, String searchKeyword) {
        return productDao.pagination(noPage,searchKeyword);
    }

    @Override
    public Integer getTotalPages() {
        return productDao.getTotalPages();
    }

    @Override
    public List<Product> sortProductName(String productName, String sortDirection) {
        return productDao.sortProductName(productName,sortDirection);
    }

    @Override
    public List<Product> findByIdList(Integer id) {
        return productDao.findByIdList(id);
    }

    @Override
    public void reduceStock(Integer productId, int quantity) {
        Product product = productDao.findById(productId);
        int currentStock = product.getStock();
        int newStock = currentStock - quantity;

        if (newStock >= 0) {
            product.setStock(newStock);
        }
    }

    @Override
    public void changeStatus(Integer id) {
        productDao.changeStatus(id);
    }
}
