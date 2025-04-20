package com.ra.controller.user;

import com.ra.model.dto.user.CustomerDto;
import com.ra.model.repository.CustomerRepository;
import com.ra.model.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class RegisterController {
    @Autowired
     CustomerService customerService;

    private final CustomerRepository customerRepository;

    @GetMapping("/register")
    public String register(Model model) {
        CustomerDto customer = new CustomerDto();
        model.addAttribute("customer", customer);
        return "user/register/register";
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("customer") CustomerDto customer, BindingResult result) {
        if (result.hasErrors()) {
            return "user/register/register";
        } else {
            if (customerRepository.existsByEmail(customer.getEmail())) {
                result.rejectValue("customerEmail", "customerEmail.exists", "Email này đã có người đăng ký");
                return "user/register/register";
            } else if (!customer.getPassword().equals(customer.getConfirmPassword())) {
                result.rejectValue("confirmPassword", "customer.exists", "Mật khẩu không trùng khớp");
                return "user/register/register";
            }
            if (customerService.register(customer)) {
                return "redirect:/login";
            }
        }
        return "redirect:/register";
    }
}
