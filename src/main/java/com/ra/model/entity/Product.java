package com.ra.model.entity;


import javax.validation.constraints.*;

public class Product {
    private int productId;
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String productName;
    @NotEmpty(message = "Không được để trống")
    private String description;
    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải là số dương")
    private double price;

    @NotNull(message = "Số lượng tồn kho không được để trống")
    private int stock;
    private String imageUrl;
    private Category category;
    private boolean productStatus=true;

    public Product() {
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isProductStatus() {
        return productStatus;
    }
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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public boolean getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(boolean productStatus) {
        this.productStatus = productStatus;
    }

}
