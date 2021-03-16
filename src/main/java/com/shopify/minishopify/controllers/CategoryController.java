package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Category;
import com.shopify.minishopify.model.Shop;
import com.shopify.minishopify.repository.CategoryRepository;
import com.shopify.minishopify.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.List;
import java.util.Optional;

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
