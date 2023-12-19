package com.ra.controller.user;

import com.ra.model.entity.CartItem;
import com.ra.model.entity.Order;
import com.ra.model.entity.OrderDetail;
import com.ra.model.entity.customer.Customer;
import com.ra.model.service.OrderDetailService;
import com.ra.model.service.OrderService;
import com.ra.model.service.cartItem.CartItemService;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class HistoryController {
    @Autowired
    OrderService orderService;
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    private HttpSession session;


    @GetMapping("/history")
    public String history(Model model){
        Customer customer= (Customer) session.getAttribute("customer");
       if (customer!=null){
           List<Order> orderList=orderService.findALLById(customer.getCustomerId());
           model.addAttribute("orderList",orderList);
           return "user/history/history";
       }
       return "redirect:/login";
    }

    @GetMapping("/bill-history/{id}")
    public String billHistory(@PathVariable("id") Integer id, Model model){
        Customer customer= (Customer) session.getAttribute("customer");
       if (customer!=null){
           Order order=orderService.findById(id);
           model.addAttribute("order" ,order);
           List<OrderDetail> orderDetailList=orderDetailService.findAll(id);
           double totalPrice=0;
           for (OrderDetail orderDetail:orderDetailList) {
               totalPrice+=orderDetail.getProduct().getPrice()*orderDetail.getQuantity();
           }
           model.addAttribute("totalPrice",totalPrice);
           model.addAttribute("orderDetailList" ,orderDetailList);
           return "user/history/bill";
       }
        return "redirect:/login";
    }

    @GetMapping("order-cancel/{id}")
    public String cancelOrder(@PathVariable("id") Integer id){
        Order order=orderService.findById(id);
       if (order.getStatus()==0){
           order.setStatus(2);
           orderService.statusOrder(id,order.getStatus());
       }
        return "redirect:/history";
    }
}
