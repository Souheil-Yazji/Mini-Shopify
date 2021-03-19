package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    /**
     * Finds and Returns a list of all the categories that have the provided string in their name.
     *
     * @param keyword the string that all results should contain in their name
     * @return A List of Categories with keyword in their name
     */
    @Query("SELECT c FROM Category c WHERE c.name LIKE %?1%")
    List<Category> search(String keyword);
}
