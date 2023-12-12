package com.ra.controller.user;

import com.ra.model.dto.user.CustomerDto;
import com.ra.model.service.CustomerService;
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
public class RegisterController {
    @Autowired
    CustomerService customerService;
    @GetMapping("/register")
    public String register(Model model){
        CustomerDto customer=new CustomerDto();
        model.addAttribute("customer", customer);
        return "user/register/register";
    }
    @PostMapping("/register")
    public String handleRegister(@Valid @ModelAttribute("customer") CustomerDto customer, BindingResult result) {
        if (result.hasErrors()) {
            return "user/register/register";
        } else {
            if (customerService.register(customer)) {
                return "redirect:/login";
            }
        }
        return "redirect:/register";
    }
}
