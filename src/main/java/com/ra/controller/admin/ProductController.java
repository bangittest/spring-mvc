package com.ra.controller.admin;

import com.ra.model.dto.product.ProductDto;
import com.ra.model.entity.Category;
import com.ra.model.entity.Image;
import com.ra.model.entity.Product;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ImagesService;
import com.ra.model.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
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
    public String index(
            @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "keyword",required = false,defaultValue = "") String keyword ,
                        Model model){
        model.addAttribute("productList", productService.pagination(page,keyword));
        model.addAttribute("keyword",keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("finalPage",productService.getTotalPages());
        return "admin/productManager/index";
    }

    public void ListPro(Model model){
        List<Category> categoryList = categoryService.findAll();
        model.addAttribute("category", categoryList);
    }

    @GetMapping("/add-product")
    public String add(Model model) {
        ProductDto productDto = new ProductDto();
        ListPro(model);
        model.addAttribute("productDto", productDto);
        return "admin/productManager/add-product";
    }

    @PostMapping("/add-product")
    public String handleAdd(@Valid @ModelAttribute("productDto") ProductDto productDto, BindingResult result,
                            @RequestParam("images") MultipartFile[] files ,Model model) {
        if (result.hasErrors()){
            ListPro(model);
            return "admin/productManager/add-product";
        }

        try {
            if (!productService.findByName(productDto.getProductName())){
                // Lưu sản phẩm và nhận lại ID mới
                String fileName=productDto.getFile1().getOriginalFilename();
                File destination= new File(path+fileName);
                int productId = productService.save(productDto);

                if (productId > 0) {
                    //up ảnh sản phẩm
                    productDto.getFile1().transferTo(destination);
                    // ảnh phụ sản phẩm
                    for (MultipartFile multipartFile : files) {
                        if (!multipartFile.isEmpty()) {
                        String fileNamesss = multipartFile.getOriginalFilename();
                        File fileDescription = new File(path + fileNamesss);
                        multipartFile.transferTo(fileDescription);

                        Image images = new Image();
                        images.setImageUrl(fileNamesss);
                        imagesService.save(images, productId);
                        }
                    }
                }
            }else {
                result.rejectValue("productName", "product.exists", "Tên sản phẩm đã tồn tại");
                ListPro(model);
                return "admin/productManager/add-product";
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("product-edit/{id}")
    public String edit(@PathVariable("id") Integer id ,Model model){
        List<Category> categoryList = categoryService.findAll();
        Product products= productService.findById(id);
        List<Image> images=imagesService.findByProductId(id);
        model.addAttribute("category", categoryList);
        model.addAttribute("images",images);
        model.addAttribute("product", products);
        return "admin/productManager/edit-product";
    }
    @PostMapping("update-product")
    public String handleUpdate(@Valid @ModelAttribute("product") Product products,BindingResult result,
                               @RequestParam("img") MultipartFile file,
                               @RequestParam("images") MultipartFile[] files,Model model) {
        if (result.hasErrors()){
            ListPro(model);
            return "admin/productManager/edit-product";
        }
        try {
            if(!productService.findByName(products.getProductName())){
                if (!file.isEmpty()) {
                    String fileName = file.getOriginalFilename();
                    File destination = new File(path + fileName);
                    products.setImageUrl(fileName);
                    file.transferTo(destination);
                }
                productService.update(products);
                for (MultipartFile additionalImage : files) {
                    if (!additionalImage.isEmpty()) {
                        String additionalFileName = additionalImage.getOriginalFilename();
                        File additionalDestination = new File(path + additionalFileName);
                        additionalImage.transferTo(additionalDestination);
                        Image images = new Image();
                        images.setImageUrl(additionalFileName);
                        imagesService.save(images, products.getProductId());
                    }
                }
            }else {
                result.rejectValue("productName", "products.exists", "Tên sản phẩm đã tồn tại");
                ListPro(model);
                return "admin/productManager/edit-product";
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "redirect:/admin/product";
    }

    @GetMapping("delete_image_product/{id}")
    public String deleteImage(@PathVariable("id") Integer id) {
        Image image=imagesService.findById(id);
        Integer idProduct=image.getProductId();
        imagesService.delete(id);
        return "redirect:/admin/product-edit/"+idProduct;
    }

//    @GetMapping("product-delete/{id}")
//    public String delete(@PathVariable("id") Integer id){
//        imagesService.deleteForeign(id);
//        productService.delete(id);
//        return "redirect:/admin/product";
//    }

    @GetMapping("product_status/{id}")
    public String changeStatus(@PathVariable("id") Integer id){
        productService.changeStatus(id);
        return "redirect:/admin/product";
    }

    @GetMapping("/sortProduct")
    public String sortCategory(@RequestParam(name = "sort",required = false,defaultValue = "") String sort,
                               @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
                               Model model) {
        List<Product> sortListProduct = productService.sortProductName(sort,sortDirection);
        model.addAttribute("productList", sortListProduct);
        model.addAttribute("currentPage", 1);
        model.addAttribute("finalPage",1);
        return "admin/productManager/index";
    }
}
