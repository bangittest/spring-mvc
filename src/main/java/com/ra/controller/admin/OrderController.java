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
public class OrderController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;

    @GetMapping("/orders")
    public String index(Model model){
        List<Order> orderList=orderService.findAll();
        model.addAttribute("orderList",orderList);
//        List<OrderDetail> orderDetailList=orderDetailService.findAll();
//        model.addAttribute("orderDetailList",orderDetailList);
        return "admin/orderManager/index";
    }
    @GetMapping("order-change/{id}")
    public String status(@PathVariable("id") Integer id){
        Order order= orderService.findById(id);
       if (order.getStatus()==0){
           order.setStatus(1);
           orderService.statusOrder(id,order.getStatus());
       }
        return "redirect:/admin/orders";
    }

    @GetMapping("order-cancel/{id}")
    public String cancel(@PathVariable("id") Integer id){
        Order order= orderService.findById(id);
       if (order.getStatus()==0){
           order.setStatus(2);
           orderService.statusOrder(id,order.getStatus());
       }
        return "redirect:/admin/orders";
    }
}
