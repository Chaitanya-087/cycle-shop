package com.chaitanya.cycleservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chaitanya.cycleservice.entity.Cycle;

public interface CycleRepository extends JpaRepository<Cycle, Long> {
    
}
