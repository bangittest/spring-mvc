package com.ra.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class WishListController {
    @RequestMapping("/wish_list")
    public String wishList(){
        return "user/wishlist/wishlist";
    }
}
