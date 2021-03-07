package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findByShopId(int shop);
}
