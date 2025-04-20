package com.ra.controller.user;

import com.ra.model.entity.Cart;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.Category;
import com.ra.model.entity.Product;
import com.ra.model.entity.customer.Customer;
import com.ra.model.repository.CategoryRepository;
import com.ra.model.repository.ProductRepository;
import com.ra.model.service.CartService;
import com.ra.model.service.CategoryService;
import com.ra.model.service.ProductService;
import com.ra.model.service.cartItem.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
public class ProductListController {


    @Autowired
    CategoryRepository categoryRepository ;

    @Autowired
    private ProductRepository productRepository ;

    @GetMapping("/products")
    public String productsList(
            @RequestParam(name = "page", defaultValue = "1") Integer page,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword,
            Model model) {
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("productsList", productRepository.findAll());
        model.addAttribute("keyword", keyword);
        model.addAttribute("currentPage", page);
        model.addAttribute("finalPage", 1);
        return "user/productList/productList";
    }

    @GetMapping("/sortProduct")
    public String sortCategory(@RequestParam(name = "sort", required = false, defaultValue = "") String sort,
                               @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
                               Model model) {
        List<Product> sortListProduct = new ArrayList<>();
        model.addAttribute("productsList", sortListProduct);
        model.addAttribute("currentPage", 1);
        model.addAttribute("finalPage", 1);
        return "user/productList/productList";
    }

    @GetMapping("products/{id}")
    public String listProductSort(@PathVariable("id") Integer id, Model model) {
        List<Product> productListCategory = new ArrayList<>();
        model.addAttribute("productsList", productListCategory);
        List<Category> categoryList = categoryRepository.findAll();
        model.addAttribute("categoryList", categoryList);
        model.addAttribute("currentPage", 1);
        model.addAttribute("finalPage", 1);
        return "user/productList/productList";

    }

}
