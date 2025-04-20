package com.ra.model.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@Entity
@Table(name = "category")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Không được để rỗng")
    private String name;

    private boolean status = true;

    public Category() {
    }

    public Category(int categoryId, String categoryName, boolean categoryStatus) {
        this.id = categoryId;
        this.name = categoryName;
        this.status = categoryStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public @NotEmpty(message = "Không được để rỗng") String getName() {
        return name;
    }

    public void setName(@NotEmpty(message = "Không được để rỗng") String name) {
        this.name = name;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}