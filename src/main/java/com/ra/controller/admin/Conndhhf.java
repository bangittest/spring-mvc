package com.ra.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class Conndhhf {
    @RequestMapping("/count")
    public String count(){
        return "admin/categoryManager/coupon-add";
    }
}
