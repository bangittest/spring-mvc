package com.ra.model.service;

import com.ra.model.dao.CustomerDao;
import com.ra.model.dto.user.CustomerDto;
import com.ra.model.entity.customer.Customer;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService{
    @Autowired
    private CustomerDao customerDao;
    @Override
    public List<Customer> findAll() {
        return customerDao.findAll();
    }

    @Override
    public boolean register(CustomerDto customerDto) {
        Customer customer=new Customer();
        customer.setCustomerName(customerDto.getCustomerName());
        customer.setCustomerEmail(customerDto.getCustomerEmail());
        customer.setAddress(customerDto.getAddress());
        customer.setPhone(customerDto.getPhone());
        String hashPass= BCrypt.hashpw(customerDto.getPassword(),BCrypt.gensalt(12));
        customer.setPassword(hashPass);
        return customerDao.save(customer);
    }

    @Override
    public boolean update(Customer customer) {
        return false;
    }

    @Override
    public void role(Integer id, String Roles) {

    }

    @Override
    public Customer checkLogin(String email, String password) {
        Customer customer=customerDao.findByEmail(email);
        if (customer!=null){
            if (BCrypt.checkpw(password,customer.getPassword())){
                return customer;
            }
        }
        return null;
    }

    @Override
    public boolean changeStatus(Integer id) {
        return customerDao.changeStatus(id);
    }

    @Override
    public Customer findByIdCustomer(Integer id) {
        return customerDao.findByIdCustomer(id);
    }

    @Override
    public boolean checkEmailRegister(String email) {
        return customerDao.checkEmailRegister(email);
    }

    @Override
    public void editProfile(Customer customer) {
        customerDao.editProfile(customer);
    }

}
