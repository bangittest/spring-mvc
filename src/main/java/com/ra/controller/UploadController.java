package com.ra.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;

@Controller
@PropertySource("classpath:config.properties")
public class UploadController {

    @Value(("${path}"))
    private String path;
    @Autowired
    private ServletContext servletContext;

    @GetMapping("/upload")
    public String upload(){
        return "form-upload";
    }

    @PostMapping("/upload")
    public String postUpload(@RequestParam("img") MultipartFile file){
        String fileName=file.getOriginalFilename();
//      String path=servletContext.getRealPath("uploads/images/");

//        String path="D:\\sevlet\\sesstion14\\src\\main\\webapp\\uploads\\images\\";
        File destination=new File(path+fileName);
        try {
            //cach 1
            file.transferTo(destination);
//            //cach 2
//            Files.write(destination.toPath(),file.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "form-upload";
    }
}
