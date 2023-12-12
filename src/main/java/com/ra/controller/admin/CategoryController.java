package com.ra.controller.admin;

import com.ra.model.entity.Category;
import com.ra.model.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

import javax.validation.Valid;
import java.util.List;


@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @RequestMapping(value = "/category")
    public String category( @RequestParam(name = "page",defaultValue = "1") Integer page,@RequestParam(name = "keyword",required = false,defaultValue = "") String keyword ,Model model){
        model.addAttribute("categoryList",categoryService.pagination(page,keyword));
        model.addAttribute("currentPage", page);
        model.addAttribute("keyword",keyword);
        model.addAttribute("finalPage",categoryService.getTotalPages());
        return "admin/categoryManager/index";
    }
    @GetMapping("/add-category")
    public String addCategory(Model model){
        Category category=new Category();
        model.addAttribute("category",category);
        return "admin/categoryManager/add-category";
    }

    @PostMapping("/create-category")
    public String createCategory(@Valid @ModelAttribute("category") Category category ,BindingResult result ) {
        if (result.hasErrors()){
            return "admin/categoryManager/add-category";
        }else {
            categoryService.save(category);
            return "redirect:/admin/category";
        }
    }

    @GetMapping("/category-edit/{id}")
    public String editCategory(@PathVariable("id") Integer id ,Model model){
        Category category=categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/categoryManager/edit-category";
    }
    @PostMapping("update-category")
    public String updateCategory(@Valid @ModelAttribute("category") Category  category,BindingResult result){
        if (result.hasErrors()){
            return "admin/categoryManager/edit-category";
        }else {
            categoryService.update(category);
            return "redirect:/admin/category";
        }
    }

    @GetMapping("/category-change/{id}")
    public String changeStatusCategory(@PathVariable("id") Integer id){
        categoryService.changeStatus(id);
        return "redirect:/admin/category";
    }
    @GetMapping("sortCategory")
    public String sortCategory(@RequestParam(name = "sort",required = false,defaultValue = "") String sort,
                               @RequestParam(name = "sortDirection", defaultValue = "ASC") String sortDirection,
                               Model model) {
        List<Category> sortedCategoryList = categoryService.sortCategoryName(sort,sortDirection);
        model.addAttribute("categoryList", sortedCategoryList);
        return "admin/categoryManager/index";
    }
}
