package com.ra.controller.user;

import com.ra.model.dto.user.CustomerDto;
import com.ra.model.entity.*;
import com.ra.model.entity.customer.Customer;
import com.ra.model.service.CartService;
import com.ra.model.service.OrderService;
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
public class CheckOutController {
    @Autowired
    HttpSession session;
    @Autowired
    OrderService orderService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    CartService cartService;


    @GetMapping("/checkout")
    public String checkOut(Model model){
        Customer customer= (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        Cart cart=cartService.findAllCartCustomer(customer.getId());
        if (cart==null){
            return "redirect:/cart";
        }
        List<CartItem> cartItemList1=cartItemService.getCarItemByCartId(cart.getId());
        if (cartItemList1.size()!=0){
            CustomerDto customerDto=new CustomerDto();
            customerDto.setEmail(customer.getEmail());
            customerDto.setName(customer.getName());
            customerDto.setPhone(customer.getPhone());
            customerDto.setAddress(customer.getAddress());
            List<CartItem> cartItemList=cartItemService.getCarItemByCartId(cart.getId());
            double totalPrice = cartService.totalPrice(cartItemList);
            model.addAttribute("cartItemList",cartItemList);
            model.addAttribute("total",totalPrice);
            model.addAttribute("customerDto", customerDto);
            return "user/checkout/checkout";
        }
       return "redirect:/cart";
    }
    @PostMapping("/checkout")
    public String handleCheckout(@ModelAttribute("customer") CustomerDto customerDto, @RequestParam("nodes") String nodes){
        Customer customer= (Customer) session.getAttribute("customer");
        Cart cart=cartService.findAllCartCustomer(customer.getId());
        List<CartItem> cartItemList=cartItemService.getCarItemByCartId(cart.getId());
        Order order=new Order();
        order.setCustomerId(customer.getId());
        order.setEmail(customerDto.getEmail());
        order.setFullName(customerDto.getName());
        order.setAddress(customerDto.getAddress());
        order.setPhone(customerDto.getPhone());
        order.setNotes(nodes);
        order.setTotalPrice(cartService.totalPrice(cartItemList));
        orderService.order(order);
        cartItemService.deleteCartId(cart.getId());
        session.removeAttribute("cart");
        session.removeAttribute("cartItems");
        return "redirect:/";
    }
}
