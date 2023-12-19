package com.ra.model.dao;

import com.ra.model.entity.Cart;

public interface CartDao {
    Cart findAllCartCustomer(Integer id);
    Integer createCart(Integer customerId);

    Cart getCartByCustomerId(Integer customerId);
}
