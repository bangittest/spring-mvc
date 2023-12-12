package com.ra.controller.user;

import com.ra.model.entity.Products;
import com.ra.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class ProductListController {
    @Autowired
    ProductService productService;
    @GetMapping("/products")
    public String productsList(Model model){
        List<Products>productsList=productService.findAll();
        model.addAttribute("productsList",productsList);
        return "user/product/product";
    }
}
