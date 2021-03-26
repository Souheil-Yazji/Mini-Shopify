package com.shopify.minishopify.controllers;

import com.shopify.minishopify.repository.ShopRepository;
import com.shopify.minishopify.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
public class TagController {
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private ShopRepository shopRepository;

    @GetMapping("/list")
    public List tagList(@RequestParam(name = "keyword", required = false) String keyword){
        if(keyword != null && !keyword.isEmpty()) {
            return tagRepository.search(keyword);
        }
        return tagRepository.findAll();
    }
}
