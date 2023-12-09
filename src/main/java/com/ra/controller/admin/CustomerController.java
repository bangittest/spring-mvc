package com.ra.controller.admin;

import com.ra.model.entity.customer.Customer;
import com.ra.model.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static com.ra.model.entity.customer.RoleName.ADMIN;

@Controller
@RequestMapping("admin")
public class CustomerController {
    @Autowired
    CustomerService customerService;
    @RequestMapping("/customer")
    public String index(Model model){
        List<Customer>customerList=customerService.findAll();
        model.addAttribute("customerList",customerList);
        return "admin/customer/index";
    }

    @GetMapping("/customer-change/{id}")
    public String changeStatus(@PathVariable("id")Integer id){
        Customer authenticatedCustomer = customerService.findByIdCustomer(id);
        if (authenticatedCustomer.getRoles().equals(ADMIN)) {
            return "redirect:/admin/customer";
        }else{
            customerService.changeStatus(id);
            return "redirect:/admin/customer";
        }
    }
}
