package com.ra.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("admin")
public class OrderController {
    @RequestMapping("/orders")
    public String index(){
        return "admin/orderManager/index";
    }
}
