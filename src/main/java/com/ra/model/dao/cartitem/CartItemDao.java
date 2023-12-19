package com.ra.model.dao.cartitem;

import com.ra.model.entity.CartItem;

import java.util.List;

public interface CartItemDao {
    List<CartItem> getCarItemByCartId(Integer cartId);
    void addToCart(CartItem cartItem);
    CartItem getCartByCartIdProId(Integer cartId, Integer productId);
    void update(Integer cartId,Integer productId, CartItem cartItem );
    void delete(Integer productId);
    List<CartItem> findAll();
    void deleteCartId(Integer cartId);

    CartItem findCartItemByProductId(Integer id);


}
