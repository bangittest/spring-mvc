package com.ra.controller.admin;

import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.service.OrderDetailService;
import com.ra.model.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("admin")
public class OrderDetailController {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    OrderService orderService;

    @GetMapping("/bills/{id}")
    public String OrderDetail(@PathVariable("id") Integer id ,Model model){
        List<OrderDetail> orderDetailList=orderDetailService.findAll(id);
        model.addAttribute("orderDetailList",orderDetailList);
        Order order=orderService.findById(id);
        model.addAttribute("order",order);
        return "admin/orderManager/invoices";
    }
}
