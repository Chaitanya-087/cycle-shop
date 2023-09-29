package com.spring.learningRest.business;

import java.util.List;

import com.spring.learningRest.entity.CartItem;

import lombok.Data;

@Data
public class CartDTO {
    private int id;
    private int totalQuantity;
    private int totalPrice;
    private List<CartItem> cartItems;
}
