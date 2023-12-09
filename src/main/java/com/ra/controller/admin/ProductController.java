package com.ra.controller.admin;

import com.ra.model.entity.Category;
import com.ra.model.entity.Products;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Controller
@PropertySource("classpath:config.properties")
@RequestMapping("admin")
public class ProductController {
    @Value("${path")
    private String path;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @RequestMapping("/product")
    public String index(Model model){
        List<Products>products=productService.findAll();
        model.addAttribute("products", products);
        return "admin/productManager/index";
    }

    @GetMapping("/add-product")
    public String add(Model model){
       List<Category> categoryList = categoryService.findAll();
      Products products=new Products();
      model.addAttribute("category", categoryList);
      model.addAttribute("product", products);
        return "admin/productManager/add-product";
    }

    @PostMapping("/create-product")
    public String handleAdd(@ModelAttribute("product") Products products ,@RequestParam("img") MultipartFile file){
        try {
            String fileName = file.getOriginalFilename();
            File destination = new File(path + fileName);

            Path filePath = Paths.get(destination.getPath());
            String fileNameOnly = filePath.getFileName().toString();

            file.transferTo(destination);

            products.setImageUrl(fileNameOnly);
            productService.save(products);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("product-edit/{id}")
    public String edit(@PathVariable("id") Integer id ,Model model){
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
        Products products= productService.findById(id);
        model.addAttribute("product", products);
        return "admin/productManager/edit-product";
    }
    @PostMapping("update-product")
    public String handleUpdate(@ModelAttribute("product") Products products){
        productService.update(products);
        return "redirect:/admin/product";
    }

    @GetMapping("product-delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        productService.delete(id);
        return "redirect:/admin/product";
    }

}
