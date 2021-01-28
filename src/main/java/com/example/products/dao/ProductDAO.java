package com.example.products.dao;

import com.example.products.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDAO extends JpaRepository<Product, Long> {

}
