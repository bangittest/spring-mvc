package com.ra.model.dto.user;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class CustomerDto {
    @NotEmpty(message = "Không được để trống")
    private String customerName;
    @Email(message = "Nhập đúng định dạng email")
    private String customerEmail;
    @NotEmpty(message = "Không được để trống trường này")
    private String address;
    @Pattern(regexp = "/^(\\([0-9]{3}\\) |[0-9]{3}-)[0-9]{3}-[0-9]{4}/",message = "Nhập số điện thoại không đúng định dạng")
    private String phone;
    @Size(min = 6 ,max = 100 ,message = "Mật khẩu ít nhất 6 kí tự")
    private String password;

    public CustomerDto() {
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
