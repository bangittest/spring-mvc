package com.ra.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class DescriptionController {
    @RequestMapping("/description")
    public String Description(){
        return "user/description/description";
    }
}
