package com.chaitanya.cycleservice.controller;

import com.chaitanya.cycleservice.entity.Cycle;
import com.chaitanya.cycleservice.service.CycleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/cycles")
public class CycleController {

  @Autowired
  private CycleService cycleService;

  @GetMapping
  public List<Cycle> getCycles() {
    return cycleService.getAllCycles();
  }
}
