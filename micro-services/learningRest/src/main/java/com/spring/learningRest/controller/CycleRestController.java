package com.spring.learningRest.controller;

import com.spring.learningRest.business.CartDTO;
import com.spring.learningRest.entity.Cart;
import com.spring.learningRest.entity.Cycle;
import com.spring.learningRest.entity.Order;
import com.spring.learningRest.repository.CartRepository;
import com.spring.learningRest.repository.CycleRepository;
import com.spring.learningRest.repository.OrderRepository;
import com.spring.learningRest.service.CartService;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/cycles")
public class CycleRestController {

    @Autowired
    private CycleRepository cycleRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    public List<Cycle> getAllCycleStocks() {
        return (List<Cycle>) cycleRepository.findAll();
    }

    @PostMapping("/{id}/borrow")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<Cycle> borrowCycle(@PathVariable("id") int id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isPresent()) {
            Cycle c = cycle.get();
            if (c.getNumAvailable() > 0) {
                c.setNumBorrowed(c.getNumBorrowed() + 1);
                cycleRepository.save(c);
            }
            return (List<Cycle>) cycleRepository.findAll();
        }
        return null;
    }

    @PostMapping("/{id}/return")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<Cycle> returnCycle(@PathVariable("id") int id) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isPresent()) {
            Cycle c = cycle.get();
            if (c.getNumAvailable() > 0) {
                c.setNumBorrowed(c.getNumBorrowed() - 1);
                cycleRepository.save(c);
            }
            return (List<Cycle>) cycleRepository.findAll();
        }
        return null;
    }

    @PostMapping("/{id}/restock")
    @ResponseBody
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    List<Cycle> restockCycle(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Optional<Cycle> cycle = cycleRepository.findById(id);
        if (cycle.isPresent()) {
            Cycle c = cycle.get();
            c.setStock(c.getStock() + quantity);
            return (List<Cycle>) cycleRepository.findAll();
        } else {
            return null;
        }
    }

    @PostMapping("cart/{id}/add")
    @ResponseBody
    @Transactional
    ResponseEntity<?> addToCart(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Cart cart = cartService.addToCart(id, quantity);
        cartService.save(cart);
        return ResponseEntity.ok().body(null);
    }

    @PostMapping("cart/{id}/remove")
    @ResponseBody
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @Transactional
    ResponseEntity<CartDTO> removeFromCart(@PathVariable("id") int id, @RequestParam("quantity") int quantity) {
        Cart cart = cartService.removeFromCart(id, quantity);
        cartRepository.save(cart);
        return ResponseEntity.ok(cartService.getCart());
    }

    @GetMapping("cart")
    @ResponseBody
    @Transactional
    ResponseEntity<CartDTO> getCart() {
        CartDTO cart = cartService.getCart();
        return ResponseEntity.ok(cart);
    }

    @PostMapping("cart/checkout/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @Transactional
    ResponseEntity<CartDTO> checkout(@PathVariable("id") int cartItemId) {
        cartService.checkout(cartItemId);
        return ResponseEntity.ok(cartService.getCart());
    }

    @PostMapping("cart/checkout/all")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @Transactional
    ResponseEntity<CartDTO> checkoutAll() {
        cartService.checkoutAll();
        return ResponseEntity.ok(cartService.getCart());
    }

    @GetMapping("/orders")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderRepository.findAll());
    }

    @GetMapping("/orders/user")
    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    ResponseEntity<List<Order>> getOrdersByUser() {
        return ResponseEntity.ok(cartService.getOrdersByUser());
    }
}
