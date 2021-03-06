package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Shop;
import com.shopify.minishopify.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("shops")
public class ShopController {

    @Autowired
    private ShopRepository shopRepository;

    @GetMapping(value="/{id}", produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Shop getShopById(@PathVariable int id) {

        Optional<Shop> shopQuery = shopRepository.findById(id);

        if (shopQuery.isPresent()) {

            return shopQuery.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No shop found with id: %d", id));
        }
    }

    @GetMapping("/")
    public List<Shop> getAllShops() {

        return shopRepository.findAll();
    }

    @PostMapping("/create")
    public Shop createShop(@RequestBody Shop shop) {

        return shopRepository.save(shop);
    }

    @PutMapping("/update/{id}")
    public Shop updateShop(@RequestBody Shop updatedShop, @PathVariable int id) {

        Optional<Shop> shopQuery = shopRepository.findById(id);

        if (shopQuery.isPresent()) {

            Shop shop = shopQuery.get();

            shop.setName(updatedShop.getName());
            shop.setDescription(updatedShop.getDescription());
            return shopRepository.save(shop);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No shop found with id: %d", id));
        }
    }
}
