package com.shopify.minishopify.controllers;

import com.shopify.minishopify.repository.ShopRepository;
import com.shopify.minishopify.repository.TagRepository;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/")
public class UtilController {
    @Autowired
    private TagRepository shopRepository;
    @Autowired
    private TagRepository productRepository;
    @Autowired
    private ShopRepository userRepository;

    @GetMapping("/stats")
    public JSONObject tagList() {
        long numShops = shopRepository.count();
        long numProducts = productRepository.count();
        long numUsers = userRepository.count();

        JSONObject response = new JSONObject();
        response.appendField("numShops", numShops);
        response.appendField("numProducts", numProducts);
        response.appendField("numUsers", numUsers);
        return response;
    }
}