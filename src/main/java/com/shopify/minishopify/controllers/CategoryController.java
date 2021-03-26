package com.shopify.minishopify.controllers;

import com.shopify.minishopify.repository.CategoryRepository;
import com.shopify.minishopify.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/list")
    public List categoryList(@RequestParam(name = "keyword", required = false) String keyword){
        if(keyword != null && !keyword.isEmpty()) {
            return categoryRepository.search(keyword);
        }
        return categoryRepository.findAll();
    }
}
