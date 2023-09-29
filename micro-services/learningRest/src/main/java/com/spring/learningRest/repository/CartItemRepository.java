package com.spring.learningRest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.learningRest.entity.CartItem;
import com.spring.learningRest.entity.Cycle;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
    
    Optional<CartItem> findByCycle(Cycle cycle);
}
