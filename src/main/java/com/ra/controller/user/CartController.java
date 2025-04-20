package com.ra.controller.user;

import com.ra.model.entity.Cart;
import com.ra.model.entity.CartItem;
import com.ra.model.entity.Product;
import com.ra.model.entity.customer.Customer;
import com.ra.model.repository.CartItemRepository;
import com.ra.model.repository.CartRepository;
import com.ra.model.service.CartService;
import com.ra.model.service.ProductService;
import com.ra.model.service.cartItem.CartItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class CartController {

    private final HttpSession session;
    @Autowired
    CartService cartService;
    @Autowired
    CartItemService cartItemService;
    @Autowired
    ProductService productService;

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @GetMapping("/cart")
    public String cart(Model model) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        Cart cart = cartRepository.findOneByCustomerId(customer.getId());
        if (Objects.nonNull(cart)) {
            List<CartItem> cartItems = cartItemRepository.getAllByCartId(cart.getId());
            model.addAttribute("cart", cart);
            session.setAttribute("cart", cart);
            double totalPrice = cartService.totalPrice(cartItems);
            model.addAttribute("cartItems", cartItems);
            model.addAttribute("totalPrice", totalPrice);
            session.setAttribute("cartItems", cartItems);
        }
        return "user/cart/cart";
    }

    @PostMapping("/add_cart")
    public String addToCart(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
//        Cart cart = (Cart) session.getAttribute("cart");
        Cart cart = cartService.findAllCartCustomer(customer.getId());
        if (cart == null) {
            cart = cartService.getOrCreateCart(customer.getId());
            session.setAttribute("cart", cart);
        }
        CartItem cartItem = cartItemService.getCartByCartIdProId(cart.getId(), productId);
        Product product = productService.findById(productId);

        if (cartItem == null) {
            CartItem newItem = new CartItem();
            newItem.setCartId(cart.getId());
            newItem.setProductId(product.getId());
            newItem.setQuantity(quantity);
            cartItemService.addToCart(newItem);
        } else {
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
            cartItemService.update(cartItem.getCartId(), productId, cartItem);
        }
        return "redirect:/cart";
    }


    @GetMapping("/increase/{id}")
    public String increase(@RequestParam("quantity") Integer quantity, @PathVariable("id") Integer idProduct) {
        CartItem cartItem = cartItemService.findCartItemByProductId(idProduct);
        Product product = productService.findById(idProduct);
        CartItem cartItem1 = cartItemService.getCartByCartIdProId(cartItem.getCartId(), idProduct);
        if (product != null && product.getStock() >= cartItem.getQuantity()) {
            cartItem1.setQuantity(cartItem1.getQuantity() + quantity);
            cartItemService.update(cartItem1.getCartId(), idProduct, cartItem1);
//        }else {
//         return
        }
        return "redirect:/cart";
    }

    @GetMapping("/reduce/{id}")
    public String reduce(@RequestParam("quantity") Integer quantity, @PathVariable("id") Integer idPro) {
//        Customer customer= (Customer) session.getAttribute("customer");
        CartItem cartItem1 = cartItemService.findCartItemByProductId(idPro);
        CartItem cartItem = cartItemService.getCartByCartIdProId(cartItem1.getCartId(), idPro);
        if (cartItem.getQuantity() - quantity <= 0) {
            cartItemService.delete(idPro);
        }
        cartItem.setQuantity(cartItem.getQuantity() - quantity);
        cartItemService.update(cartItem.getCartId(), idPro, cartItem);
        return "redirect:/cart";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        cartItemService.delete(id);
        return "redirect:/cart";
    }
}
