package com.ra.controller.admin;

import com.ra.model.entity.Category;
import com.ra.model.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/admin")
public class CategoryController {
    @Autowired
    CategoryService categoryService;
    @RequestMapping("/category")
    public String category( @RequestParam(name = "id",defaultValue = "1") Integer id,@RequestParam(name = "keyword",required = false,defaultValue = "") String keyword ,Model model){
        model.addAttribute("categoryList",categoryService.pagination(id,keyword));
        model.addAttribute("keyword",keyword);
//        model.addAttribute("sortName", sortName);
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
    public String createCategory(@ModelAttribute("category") Category category){
        categoryService.save(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category-edit/{id}")
    public String editCategory(@PathVariable("id") Integer id ,Model model){
        Category category=categoryService.findById(id);
        model.addAttribute("category", category);
        return "admin/categoryManager/edit-category";
    }
    @PostMapping("update-category")
    public String updateCategory(@ModelAttribute("category") Category  category){
        categoryService.update(category);
        return "redirect:/admin/category";
    }

    @GetMapping("/category-change/{id}")
    public String changeStatusCategory(@PathVariable("id") Integer id){
        categoryService.changeStatus(id);
        return "redirect:/admin/category";
    }

}
