package com.ra.model.service;

import com.ra.model.dao.CartDao;
import com.ra.model.entity.Cart;
import com.ra.model.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService{
    @Autowired
    CartDao cartDao;

    @Override
    public Cart findAllCartCustomer(Integer id) {
        return cartDao.findAllCartCustomer(id);
    }

    @Override
    public Cart getOrCreateCart(Integer customerId) {
        Cart checkCart=cartDao.getCartByCustomerId(customerId);
        if (checkCart!=null){
            return checkCart;
        }else {
            Integer newCartId=cartDao.createCart(customerId);
            Cart newCart=new Cart();
            newCart.setId(newCartId);
            newCart.setCustomerId(customerId);
            return newCart;
        }
    }

    @Override
    public double totalPrice(List<CartItem> cartItemList) {
        double totalPrice =0;
        for (CartItem cartItem : cartItemList) {
            totalPrice += cartItem.getQuantity()*cartItem.getProductId();
        }
//        double totalPrice=cartItemList.stream().mapToDouble(cart->cart.getQuantity()*cart.getProduct().getPrice()).sum();
        return totalPrice;
    }
}
