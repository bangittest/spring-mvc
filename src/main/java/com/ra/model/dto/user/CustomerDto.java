package com.ra.model.dto.user;
import javax.validation.Constraint;
import javax.validation.constraints.*;

public class CustomerDto {
    @NotEmpty(message = "Không được để trống")
    private String customerName;
    @NotEmpty(message = "Không được để trống")
    @Email(message = "Nhập đúng định dạng email")
    private String customerEmail;
    @NotEmpty(message = "Không được để trống trường này")
    private String address;
    @Pattern(regexp =  "^(03[2-9]|07[0-9]|08[1-9]|09[0-9])\\d{7}$",message = "Nhập số điện thoại không đúng định dạng")
    private String phone;
    @Size(min = 6 ,max = 100 ,message = "Mật khẩu ít nhất 6 kí tự")
    private String password;
    @Size(min = 6 ,max = 100 ,message = "Nhập trùng khớp mật khẩu")
    private String confirmPassword;

    public CustomerDto() {
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
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
