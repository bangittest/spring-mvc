package com.ra.model.entity;

import com.ra.model.entity.customer.Customer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer customerId;
    private String email;
    private String fullName;
    private String address;
    private String phone;

    private String notes;
    @Column(name = "order_date")
    private Date date;
    @Column(name = "order_status")
    private int status = 0;
    @Column(name = "total")
    private Double totalPrice;
}
