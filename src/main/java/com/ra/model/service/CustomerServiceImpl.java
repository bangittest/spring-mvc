package com.ra.model.service;

import com.ra.model.dao.CustomerDao;
import com.ra.model.dto.user.CustomerDto;
import com.ra.model.entity.customer.Customer;
import com.ra.model.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    @Autowired
    private CustomerDao customerDao;

    private final CustomerRepository customerRepository;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    @Transactional
    public boolean register(CustomerDto customerDto) {
        try {
            Customer customer = new Customer();
            customer.setName(customerDto.getName());
            customer.setEmail(customerDto.getEmail());
            customer.setAddress(customerDto.getAddress());
            customer.setPhone(customerDto.getPhone());
            customer.setStatus(true);
            String hashPass = BCrypt.hashpw(customerDto.getPassword(), BCrypt.gensalt(12));
            customer.setPassword(hashPass);
            customerRepository.save(customer);
            return true;
        }catch (Exception e){
            return false;
        }
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
        Customer customer = customerRepository.findByEmail(email);
        if (customer != null) {
            if (BCrypt.checkpw(password, customer.getPassword())) {
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
