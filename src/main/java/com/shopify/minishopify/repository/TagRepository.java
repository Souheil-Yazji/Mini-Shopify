package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
