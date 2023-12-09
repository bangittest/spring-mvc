package com.ra.model.service;

import com.ra.model.dto.user.CustomerDto;
import com.ra.model.entity.customer.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    boolean register(CustomerDto customerDto);
    boolean update(Customer customer);
    void role(Integer id,String Roles);
    Customer checkLogin(String email,String password);
    boolean changeStatus(Integer id);
    Customer findByIdCustomer(Integer id);
}
