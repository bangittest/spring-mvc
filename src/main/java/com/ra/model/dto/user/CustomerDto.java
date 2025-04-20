package com.ra.model.dto.user;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Constraint;
import javax.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
    @NotEmpty(message = "Không được để trống")
    private String name;
    @NotEmpty(message = "Không được để trống")
    @Email(message = "Nhập đúng định dạng email")
    private String email;
    @NotEmpty(message = "Không được để trống trường này")
    private String address;
    @Pattern(regexp =  "^(03[2-9]|07[0-9]|08[1-9]|09[0-9])\\d{7}$",message = "Nhập số điện thoại không đúng định dạng")
    private String phone;
    @Size(min = 6 ,max = 100 ,message = "Mật khẩu ít nhất 6 kí tự")
    private String password;
    @Size(min = 6 ,max = 100 ,message = "Nhập trùng khớp mật khẩu")
    private String confirmPassword;



}
