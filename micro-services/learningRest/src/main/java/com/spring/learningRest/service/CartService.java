package com.spring.learningRest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.spring.learningRest.business.CartDTO;
import com.spring.learningRest.entity.Cart;
import com.spring.learningRest.entity.CartItem;
import com.spring.learningRest.entity.Cycle;
import com.spring.learningRest.entity.Order;
import com.spring.learningRest.entity.User;
import com.spring.learningRest.repository.CartItemRepository;
import com.spring.learningRest.repository.CartRepository;
import com.spring.learningRest.repository.CycleRepository;
import com.spring.learningRest.repository.OrderRepository;
import com.spring.learningRest.repository.UserRepository;

@Service
public class CartService {
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    public CartDTO getCart() {
        return createCartDTO();
    }

    private CartDTO createCartDTO() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(authentication.getName()).get();
        Cart cart = cartRepository.findByUser(user).orElseGet(Cart::new);
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cart.getId());
        cartDTO.setCartItems(cart.getCartItems());
        cartDTO.setTotalPrice(cart.getCartItems().stream().mapToInt(CartItem::getTotalPrice).sum());
        cartDTO.setTotalQuantity(cart.getCartItems().stream().mapToInt(CartItem::getQuantity).sum());
        return cartDTO;
    }

    public Cart save(Cart cart) {
        return cartRepository.save(cart);
    }

    public Cart addToCart(int cycleId, int quantity) {
        Cycle cycle = cycleRepository.findById(cycleId).orElseThrow(() -> new RuntimeException("Cycle not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            cartRepository.save(newCart);
            return newCart;
        });

        CartItem cartItem = cart.getCartItems().stream().filter(item -> item.getCycle().getId() == cycle.getId())
                .findFirst().orElseGet(() -> {
                    CartItem newCartItem = new CartItem();
                    newCartItem.setCycle(cycle);
                    cart.getCartItems().add(newCartItem);
                    return newCartItem;
                });

        int maxQuantity = Math.min(quantity + cartItem.getQuantity(), cycle.getStock());
        cartItem.setQuantity(maxQuantity);
        cartItem.setTotalPrice(cycle.getPrice() * cartItem.getQuantity());
        return cart;
    }

    public Cart removeFromCart(int cycleId, int quantity) {
        if (quantity <= 0) {
            return null;
        }
    
        Cycle cycle = cycleRepository.findById(cycleId)
                .orElseThrow(() -> new RuntimeException("Cycle not found"));

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(authentication.getName()).get();

        Cart cart = cartRepository.findByUser(user).orElseGet(Cart::new);

        Optional<CartItem> existingCartItem = cart.getCartItems().stream()
                .filter(item -> item.getCycle().getId() == cycle.getId())
                .findFirst();

        if (existingCartItem.isPresent()) {
            CartItem cartItem = existingCartItem.get();
            int newQuantity = cartItem.getQuantity() - quantity;
    
            if (newQuantity <= 0) {
                cart.getCartItems().remove(cartItem);
                cartItemRepository.delete(cartItem);
            } else {
                cartItem.setQuantity(newQuantity);
                cartItem.setTotalPrice(cycle.getPrice() * newQuantity);
            }
        }
        return cart;
    }

    public Cart checkout(int cartItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(authentication.getName()).get();

        Cart cart = cartRepository.findByUser(user).orElseGet(Cart::new);

        CartItem cartItem = cart.getCartItems().stream()
                .filter(item -> item.getId() == cartItemId)
                .findFirst().orElseThrow(() -> new RuntimeException("Cart item not found"));

        Cycle cycle = cartItem.getCycle();
        int quantity = cartItem.getQuantity();

        cycle.setStock(cycle.getStock() - quantity);
        cycleRepository.save(cycle);

        Order order = orderRepository.findByUserAndCycle(user, cycle).orElseGet(() -> {
            Order newOrder = new Order();
            newOrder.setUser(user);
            newOrder.setCycle(cycle);
            newOrder.setQuantity(quantity);
            return newOrder;
        });
        orderRepository.save(order);

        cart.getCartItems().remove(cartItem);
        cartItemRepository.delete(cartItem);
        return cart;
    }

    public Cart checkoutAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(authentication.getName()).get();
        Cart cart = cartRepository.findByUser(user).orElseGet(Cart::new);

        for (CartItem cartItem : cart.getCartItems()) {
            Cycle cycle = cartItem.getCycle();
            int quantity = cartItem.getQuantity();
            cycle.setStock(cycle.getStock() - quantity);
            cycleRepository.save(cycle);

            Order order = orderRepository.findByUserAndCycle(user, cycle).orElseGet(() -> {
                Order newOrder = new Order();
                newOrder.setUser(user);
                newOrder.setCycle(cycle);
                newOrder.setQuantity(quantity);
                return newOrder;
            });
            orderRepository.save(order);
        }

        cart.getCartItems().clear();
        return cart;
    }


    public List<Order> getOrdersByUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByName(authentication.getName()).get();
        return orderRepository.findByUser(user);
    }
}
