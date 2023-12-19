package com.ra.controller.user;

import com.ra.model.entity.customer.Customer;
import com.ra.model.service.CustomerService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;

@Controller
@RequestMapping("/")
public class ProfileController {
    @Value("D:\\sevlet\\pj4\\src\\main\\webapp\\uploads\\avatar\\")
    private String path;
    @Autowired
    HttpSession session;
    @Autowired
    CustomerService customerService;
    @GetMapping("/profile")
    public String profile(Model model){
        Customer customer= (Customer) session.getAttribute("customer");
       if (customer!=null){
           model.addAttribute("customer",customer);
           return "user/profile/profile";
       }
       return "redirect:/";
    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("customer") Customer customer, @RequestParam("img") MultipartFile file ){
        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File destination = new File(path + fileName);
                customer.setImage(fileName);
                file.transferTo(destination);
            }
            customerService.editProfile(customer);
           session.setAttribute("customer",customer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/profile";
    }
}
