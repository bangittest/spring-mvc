package com.ra.model.entity.customer;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class Customer {
    private int customerId;
    private String customerName;
    @NotEmpty(message = "Email không được bỏ trống")
    @Email(message = "không đúng định dạng email")
    private String customerEmail;
    private String address;
    private String phone;
    @NotEmpty(message = "Password không được bỏ trông")
    @Size(min = 6, message = "Password cần ít nhất 6 kí tự")
    private String password;
    private String image;
    private RoleName roles=RoleName.USER;
    private boolean Status=true;

    public Customer() {
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public void setCustomerEmail(String customerEmail) {
        this.customerEmail = customerEmail;
    }

    public String getAddress() {
        return address;
    }

    public RoleName getRoles() {
        return roles;
    }

    public void setRoles(RoleName roles) {
        this.roles = roles;
    }

    public boolean isStatus() {
        return Status;
    }

    public void setStatus(boolean status) {
        Status = status;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
