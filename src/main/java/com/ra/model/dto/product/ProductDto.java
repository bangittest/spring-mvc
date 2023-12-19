package com.ra.model.dto.product;

import com.ra.model.ValidImage.FileNotNull;
import com.ra.model.ValidImage.ValidImage;
import com.ra.model.entity.Category;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class ProductDto {
    private int productId;
    @NotEmpty(message = "Không được để trống")
    private String productName;
    @NotEmpty(message = "Không được để trống")
    private String description;
    @NotNull(message = "Không được để trống")
    private double price;
    @NotNull(message = "Không được để trống")
    private int stock;
    @FileNotNull(message = "Không được để trống")
    @ValidImage(type = {"image/jpeg","image/png","image/jpg"},message = "Khong dung dinh dang nhe")
    private MultipartFile file1;
    private Category category;
    private boolean productStatus=true;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public MultipartFile getFile1() {
        return file1;
    }

    public void setFile1(MultipartFile file1) {
        this.file1 = file1;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean isProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }
}
