package com.chaitanya.cycleservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chaitanya.cycleservice.entity.Cycle;
import com.chaitanya.cycleservice.repository.CycleRepository;

@Service
public class CycleService {
    
    @Autowired
    private CycleRepository cycleRepository;

    public List<Cycle> getAllCycles() {
        return cycleRepository.findAll();
    }
}
