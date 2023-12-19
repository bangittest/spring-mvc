package com.ra.model.service;

import com.ra.model.entity.Cart;
import com.ra.model.entity.CartItem;

import java.util.List;

public interface CartService {
    Cart findAllCartCustomer(Integer id);
    Cart getOrCreateCart(Integer customerId);
    double totalPrice(List<CartItem> cartItemList);
}
