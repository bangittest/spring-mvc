package com.ra.controller.admin;

import com.ra.model.entity.Category;
import com.ra.model.entity.Images;
import com.ra.model.entity.Products;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ImagesService;
import com.ra.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("admin")
public class ProductController {
    @Value("D:\\sevlet\\pj4\\src\\main\\webapp\\uploads\\images\\")
    private String path;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImagesService imagesService;

    @RequestMapping("/product")
    public String index(Model model) {
        List<Products> products = productService.findAll();
        model.addAttribute("products", products);
        return "admin/productManager/index";
    }

    @GetMapping("/add-product")
    public String add(Model model) {
        List<Category> categoryList = categoryService.findAll();
        Products products = new Products();
        model.addAttribute("category", categoryList);
        model.addAttribute("product", products);
        return "admin/productManager/add-product";
    }

    @PostMapping("/create-product")
    public String handleAdd(@ModelAttribute("product") Products products,
                            @RequestParam("img") MultipartFile file,
                            @RequestParam("images") MultipartFile[] files) {

        try {
            // Lưu sản phẩm và nhận lại ID mới
            String fileName=file.getOriginalFilename();
            File destination= new File(path+fileName);
            products.setImageUrl(fileName);
            int productId = productService.save(products);

            if (productId > 0) {
                //up ảnh sản phẩm
                file.transferTo(destination);

                // ảnh phụ sản phẩm
                for (MultipartFile multipartFile : files) {
                    String fileNamesss = multipartFile.getOriginalFilename();
                    File fileDescription = new File(path + fileNamesss);
                    multipartFile.transferTo(fileDescription);

                    Images images = new Images();
                    images.setImageUrl(fileNamesss);
                    imagesService.save(images, productId);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }


    @GetMapping("product-edit/{id}")
    public String edit(@PathVariable("id") Integer id ,Model model){
        List<Category> categoryList = categoryService.findAll();
        Products products= productService.findById(id);
        List<Images> images=imagesService.findByProductId(id);
        model.addAttribute("category", categoryList);
        model.addAttribute("images",images);
        model.addAttribute("product", products);
        return "admin/productManager/edit-product";
    }
    @PostMapping("update-product")
    public String handleUpdate(@ModelAttribute("product") Products products,
                               @RequestParam("img") MultipartFile file,
                               @RequestParam("images") MultipartFile[] files) {

        try {
            if (!file.isEmpty()) {
                String fileName = file.getOriginalFilename();
                File destination = new File(path + fileName);
                products.setImageUrl(fileName);
                file.transferTo(destination);
            }
            productService.update(products);
            imagesService.deleteForeign(products.getProductId());
            for (MultipartFile additionalImage : files) {
                if (!additionalImage.isEmpty()) {
                    String additionalFileName = additionalImage.getOriginalFilename();
                    File additionalDestination = new File(path + additionalFileName);
                    additionalImage.transferTo(additionalDestination);
                    Images images = new Images();
                    images.setImageUrl(additionalFileName);
                    imagesService.save(images, products.getProductId());
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }

//    @GetMapping("delete/{idImage}")
//    public String deleteImage(@PathVariable("idImage") Integer idImage) {
//        imagesService.delete(idImage);
//        return "redirect:/admin/edit-product";
//    }



    @GetMapping("product-delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        imagesService.deleteForeign(id);
        productService.delete(id);
        return "redirect:/admin/product";
    }

}
