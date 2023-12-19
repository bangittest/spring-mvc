package com.ra.model.dao;

import com.ra.model.dto.user.CustomerDto;
import com.ra.model.entity.customer.Customer;

import java.util.List;

public interface CustomerDao {
    List<Customer> findAll();
    boolean save(Customer customer);
    boolean update(Customer customer);
    void role(Integer id,String Roles);
    Customer findByEmail(String email);
    boolean changeStatus(Integer id);

    Customer findByIdCustomer(Integer id);
    boolean checkEmailRegister(String email);

    void editProfile(Customer customer);
}
