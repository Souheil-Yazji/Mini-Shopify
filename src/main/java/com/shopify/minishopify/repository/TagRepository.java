package com.shopify.minishopify.repository;

import com.shopify.minishopify.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    /**
     * Finds and Returns a list of all the tags that have the provided string in their name.
     *
     * @param keyword the string that all results should contain in their name
     * @return A List of Tags with keyword in their name
     */
    @Query("SELECT t FROM Tag t WHERE t.name LIKE %?1%")
    List<Tag> search(String keyword);
}
