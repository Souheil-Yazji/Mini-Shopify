package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    @Query("SELECT s FROM Shop s WHERE CONCAT(s.name, ' ', s.description) LIKE %?1%")
    public List<Shop> search(String keyword);
}
