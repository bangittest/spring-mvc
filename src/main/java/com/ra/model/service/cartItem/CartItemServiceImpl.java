package com.ra.model.service.cartItem;

import com.ra.model.dao.cartitem.CartItemDao;
import com.ra.model.entity.CartItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class CartItemServiceImpl implements CartItemService{
    @Autowired
    CartItemDao cartItemDao;
    @Override
    public List<CartItem> getCarItemByCartId(Integer cartId) {
        return cartItemDao.getCarItemByCartId(cartId);
    }

    @Override
    public void addToCart(CartItem cartItem) {
        cartItemDao.addToCart(cartItem);
    }

    @Override
    public CartItem getCartByCartIdProId(Integer cartId, Integer productId) {
        return  cartItemDao.getCartByCartIdProId(cartId,productId);
    }

    @Override
    public void update(Integer cartId, Integer productId, CartItem cartItem) {
        cartItemDao.update(cartId,productId,cartItem);
    }

    @Override
    public void delete(Integer productId) {
        cartItemDao.delete(productId);
    }

    @Override
    public List<CartItem> findAll() {
        return cartItemDao.findAll();
    }

    @Override
    public void deleteCartId(Integer cartId) {
        cartItemDao.deleteCartId(cartId);
    }

    @Override
    public CartItem findCartItemByProductId(Integer id) {
        return cartItemDao.findCartItemByProductId(id);
    }
}
