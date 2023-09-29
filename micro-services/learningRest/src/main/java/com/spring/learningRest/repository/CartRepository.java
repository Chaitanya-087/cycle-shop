package com.spring.learningRest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.learningRest.entity.Cart;
import com.spring.learningRest.entity.User;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    
    Optional<Cart> findByUser(User user);


}
