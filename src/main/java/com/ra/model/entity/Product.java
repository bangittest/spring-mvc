package com.ra.model.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.*;

@Getter
@Setter
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotEmpty(message = "Tên sản phẩm không được để trống")
    private String name;
    @NotEmpty(message = "Không được để trống")
    private String description;
    @NotNull(message = "Giá không được để trống")
    @Positive(message = "Giá phải là số dương")
    private Double price;
    @NotNull(message = "Số lượng tồn kho không được để trống")
    private int stock;

    private String urlImage;
    private Integer categoryId;
    private boolean status = true;

    public Product() {
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }


    public @NotEmpty(message = "Không được để trống") String getDescription() {
        return description;
    }

    public void setDescription(@NotEmpty(message = "Không được để trống") String description) {
        this.description = description;
    }

    public @NotNull(message = "Giá không được để trống") @Positive(message = "Giá phải là số dương") Double getPrice() {
        return price;
    }

    public void setPrice(@NotNull(message = "Giá không được để trống") @Positive(message = "Giá phải là số dương") Double price) {
        this.price = price;
    }

    @NotNull(message = "Số lượng tồn kho không được để trống")
    public int getStock() {
        return stock;
    }

    public void setStock(@NotNull(message = "Số lượng tồn kho không được để trống") int stock) {
        this.stock = stock;
    }


    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }


}
