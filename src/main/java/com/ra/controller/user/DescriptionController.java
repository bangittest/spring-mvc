package com.ra.controller.user;

import com.ra.model.entity.Image;
import com.ra.model.entity.Product;
import com.ra.model.service.ImagesService;
import com.ra.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class DescriptionController {
    @Autowired
    ProductService productService;
    @Autowired
    ImagesService imagesService;
    @GetMapping("/detail/{id}")
    public String Description(@PathVariable("id") Integer id , Model model){
        List<Product>prod=productService.findAll();
        Product product=productService.findById(id);
        List<Image>imageList=imagesService.findByProductId(product.getId());
        model.addAttribute("prod",prod);
        model.addAttribute("imageList",imageList);
        model.addAttribute("description",product);
        return "user/description/description";
    }
}
