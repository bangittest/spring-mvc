package com.ra.controller.user;

import com.ra.model.entity.customer.Customer;
import com.ra.model.entity.customer.RoleName;
import com.ra.model.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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
    public String handleLogin( @ModelAttribute("customer") Customer customer, RedirectAttributes redirectAttributes) {
//        if (result.hasErrors()) {
//            return "user/login/login";
//        }
        Customer authenticatedCustomer = customerService.checkLogin(customer.getEmail(), customer.getPassword());

        if (authenticatedCustomer != null) {
            if (!authenticatedCustomer.isStatus()) {
                redirectAttributes.addFlashAttribute("message", "Tài khoản đã bị khóa. Vui lòng liên hệ hỗ trợ.");
                return "user/login/login";
            }
            if (authenticatedCustomer.getRoles().equals(RoleName.USER)) {
                httpSession.setAttribute("customer", authenticatedCustomer);
                redirectAttributes.addFlashAttribute("success","Đăng nhập thanh công");
                return "redirect:/";
            }
        }
        return "user/login/login";
    }
    @GetMapping("/logout")
    public String logout(){
        httpSession.removeAttribute("customer");
        return "user/home";
    }

    @GetMapping("/admin_login")
    public String loginAdmin(){
        return "admin/login";
    }
    @PostMapping("/login_admin")
    public String handleLoginAdmin(@RequestParam("email") String email,
                                   @RequestParam("password") String password){

        Customer customer = customerService.checkLogin(email,password);
        if(customer != null){
            if(customer.getRoles().equals(ADMIN)){
                httpSession.setAttribute("admin",customer);
                return "admin/home";
            }
        }
        return "admin/login";
    }

    @GetMapping("/admin/logout")
    public String logoutAdmin(){
        httpSession.removeAttribute("admin");
        httpSession.removeAttribute("cart");
        httpSession.removeAttribute("cartItems");
        return "admin/login";
    }

}

