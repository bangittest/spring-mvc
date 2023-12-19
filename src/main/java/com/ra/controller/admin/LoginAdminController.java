//package com.ra.controller.admin;
//
//import com.ra.model.entity.customer.Customer;
//import com.ra.model.service.CustomerService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import javax.servlet.http.HttpSession;
//
//import static com.ra.model.entity.customer.RoleName.ADMIN;
//
//@Controller
//public class LoginAdminController {
//    @Autowired
//    CustomerService customerService;
//    @Autowired
//    HttpSession session;
//    @GetMapping("/logon")
//    public String loginAdmin(){
//        return "admin/login";
//    }
//    @PostMapping("/login")
//    public String handleLoginAdmin(@RequestParam("email") String email, @RequestParam("password") String password){
//
//        Customer customer = customerService.checkLogin(email,password);
//        if(customer != null){
//            if(customer.getRoles().equals(ADMIN)){
//                session.setAttribute("admin",customer);
//                return "admin/home";
//            }
//        }
//        return "admin/login";
//    }
//
//    @GetMapping("/logout")
//    public String logoutAdmin(){
//        session.removeAttribute("admin");
//        return "admin/login";
//    }
//}
