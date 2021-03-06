package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
