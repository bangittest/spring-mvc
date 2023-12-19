package com.ra.controller.user;

import com.ra.model.entity.Cart;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.Product;
import com.ra.model.entity.customer.Customer;
import com.ra.model.service.CartService;
import com.ra.model.service.ProductService;
import com.ra.model.service.cartItem.CartItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/")
public class CartController {
    @Autowired
    HttpSession session;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ProductService productService;
    @GetMapping("/cart")
    public String cart(Model model){
        Customer customer= (Customer) session.getAttribute("customer");
        if (customer==null){
            return "redirect:/login";
        }
        Cart cart=cartService.getOrCreateCart(customer.getCustomerId());
        List<CartItem>cartItems=cartItemService.getCarItemByCartId(cart.getCartId());
        model.addAttribute("cart",cart);
        session.setAttribute("cart" ,cart);
        double totalPrice= cartService.totalPrice(cartItems);
        model.addAttribute("cartItems",cartItems);
        model.addAttribute("totalPrice",totalPrice);
        session.setAttribute("cartItems",cartItems);
        return "user/cart/cart";
    }

    @PostMapping("/add_cart")
    public String addToCart(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        Cart cart = (Cart) session.getAttribute("cart");
        if (cart == null) {
            cart = cartService.getOrCreateCart(customer.getCustomerId());
            session.setAttribute("cart", cart);
        }
        CartItem cartItem = cartItemService.getCartByCartIdProId(customer.getCustomerId(), productId);
        Product product = productService.findById(productId);

        if (cartItem == null) {
            if (product != null && product.getStock() >= quantity) {
                CartItem newItem = new CartItem();
                newItem.setCartId(cart.getCartId());
                newItem.setProduct(product);
                newItem.setQuantity(quantity);
                cartItemService.addToCart(newItem);
            } else {
                return "redirect:/detail/"+productId;
            }
        } else {
            if (product != null && product.getStock() >= cartItem.getQuantity() + quantity) {
                cartItem.setQuantity(cartItem.getQuantity() + quantity);
                cartItemService.update(cartItem.getCartId(), productId, cartItem);
            } else {
                return "redirect:/detail/"+productId;
            }
        }
        return "redirect:/cart";
    }


    @GetMapping("/increase/{id}")
    public String increase(@RequestParam("quantity") Integer quantity ,@PathVariable("id") Integer idProduct){
        CartItem cartItem=cartItemService.findCartItemByProductId(idProduct);
        Product product=productService.findById(idProduct);
            CartItem cartItem1 = cartItemService.getCartByCartIdProId(cartItem.getCartId(), idProduct);
        if (product!=null && product.getStock()>=cartItem.getQuantity()){
            cartItem1.setQuantity(cartItem1.getQuantity() + quantity);
            cartItemService.update(cartItem1.getCartId(),idProduct,cartItem1);
//        }else {
//         return
        }
        return "redirect:/cart";
    }
    @GetMapping("/reduce/{id}")
    public String reduce(@RequestParam("quantity") Integer quantity,@PathVariable("id") Integer idPro){
//        Customer customer= (Customer) session.getAttribute("customer");
        CartItem cartItem1=cartItemService.findCartItemByProductId(idPro);
        CartItem cartItem = cartItemService.getCartByCartIdProId(cartItem1.getCartId(), idPro);
        if (cartItem.getQuantity() - quantity<=0){
            cartItemService.delete(idPro);
        }
        cartItem.setQuantity(cartItem.getQuantity() - quantity);
        cartItemService.update(cartItem.getCartId(),idPro,cartItem);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id){
        cartItemService.delete(id);
        return "redirect:/cart";
    }
}
