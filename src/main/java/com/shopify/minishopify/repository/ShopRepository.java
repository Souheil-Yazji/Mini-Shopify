package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
}
