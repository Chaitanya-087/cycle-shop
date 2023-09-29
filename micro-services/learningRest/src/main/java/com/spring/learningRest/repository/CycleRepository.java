package com.spring.learningRest.repository;

import org.springframework.data.repository.CrudRepository;

import com.spring.learningRest.entity.Cycle;

public interface CycleRepository extends CrudRepository<Cycle, Integer> {

}