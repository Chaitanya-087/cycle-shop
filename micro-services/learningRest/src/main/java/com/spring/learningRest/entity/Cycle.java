package com.spring.learningRest.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "cycles", uniqueConstraints = { @UniqueConstraint(columnNames = { "brand" }) })
@Data
public class Cycle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String brand;
    private int stock;
    private int numBorrowed;
    private int price;
    public int getNumAvailable() {
        return stock - numBorrowed;
    }

}