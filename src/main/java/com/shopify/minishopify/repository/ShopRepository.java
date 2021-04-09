package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ShopRepository extends JpaRepository<Shop, Integer> {
    /**
     * Queries and return a list of shops that contains the keyword in their name or description
     *
     * @param keyword string that will be used for query
     * @return A List of shops with keyword in their name or description
     */
    @Query("SELECT s FROM Shop s WHERE lower(CONCAT(s.name, ' ', s.description)) LIKE %?1%")
    List<Shop> search(String keyword);

    /**
     * Queries and return a list of shops that contains the keyword in their tag
     *
     * @param keyword string that will be used for query
     * @return A List of shops with keyword in their tags
     */
    @Query("SELECT DISTINCT s FROM Shop s JOIN s.tags t WHERE lower(t.name) LIKE %?1%")
    List<Shop> searchThroughTag(String keyword);

    /**
     * Queries and return a list of shops that contains the keyword in their category
     *
     * @param keyword string that will be used for query
     * @return A List of shops with keyword in their categories
     */
    @Query("SELECT DISTINCT s FROM Shop s JOIN s.categories c WHERE lower(c.name) LIKE %?1%")
    List<Shop> searchThroughCategory(String keyword);

}
