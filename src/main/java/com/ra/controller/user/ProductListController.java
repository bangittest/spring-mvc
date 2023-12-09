package com.ra.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class ProductListController {
    @RequestMapping("/products")
    public String productslist(){
        return "user/product/product";
    }
}
