package com.ra.controller.user;

import com.ra.model.entity.customer.Customer;
import com.ra.model.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import static com.ra.model.entity.customer.RoleName.ADMIN;

@Controller
@RequestMapping("/")
public class LoginController {

    @Autowired
    CustomerService customerService;
    @Autowired
    HttpSession httpSession;


    @GetMapping("/login")
    public String login(Model model) {
        Customer customer = new Customer();
        model.addAttribute("customer", customer);
        return "user/login/login";
    }

    @PostMapping("/login")
    public String handleLogin(@Valid @ModelAttribute("customer") Customer customer, Model model, BindingResult result , RedirectAttributes redirectAttributes) {
        if (result.hasErrors()){
            return "user/login/login";
        }
        Customer authenticatedCustomer = customerService.checkLogin(customer.getCustomerEmail(), customer.getPassword());

        if (authenticatedCustomer != null) {
            if (!authenticatedCustomer.isStatus()) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản đã bị khóa. Vui lòng liên hệ hỗ trợ.");
                return "user/login/login";
            }
            if (authenticatedCustomer.getRoles().equals(ADMIN)) {
                model.addAttribute("customer", authenticatedCustomer);
                httpSession.setAttribute("customer", authenticatedCustomer);
                return "admin/home";
            } else {
                model.addAttribute("customer", authenticatedCustomer);
                httpSession.setAttribute("customer", authenticatedCustomer);
                return "user/home";
            }
        }else {
            return "user/login/login";
        }
    }
    @GetMapping("/logout")
    public String logout(){
        httpSession.removeAttribute("customer");
        return "user/home";
    }
}

