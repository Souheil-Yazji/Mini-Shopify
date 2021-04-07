package com.shopify.minishopify.controllers;

import com.shopify.minishopify.model.Shop;
import com.shopify.minishopify.repository.ShopRepository;
import com.shopify.minishopify.util.ImageValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/shops")
public class ShopController {
    private static final Logger LOG = LoggerFactory.getLogger(ShopController.class);

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
    public Shop createShop(@Valid @RequestBody Shop shop) {
        LOG.info("Created Shop {} for User {}", shop.getName(), shop.getOwner().getName());

        return shopRepository.save(shop);
    }

    @PutMapping("/update/{id}")
    public Shop updateShop(@Valid @RequestBody Shop updatedShop, @PathVariable int id) {

        Optional<Shop> shopQuery = shopRepository.findById(id);

        if (shopQuery.isPresent()) {
            if(!ImageValidator.isValidImage(updatedShop.getImage())) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Invalid image"));
            }

            Shop shop = shopQuery.get();
            shop.setName(updatedShop.getName());
            shop.setDescription(updatedShop.getDescription());
            shop.setCategories(updatedShop.getCategories());
            shop.setTags(updatedShop.getTags());
            shop.setImage(updatedShop.getImage());

            LOG.info("Updated Shop id:{} for User {}", shop.getId(), shop.getOwner().getName());
            return shopRepository.save(shop);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("No shop found with id: %d", id));
        }
    }

    @GetMapping("/list")
    public List storeList(@RequestParam(name = "keyword", required = false) String keyword){
        if(keyword != null){
            if(!keyword.equals("")) {
                List<Shop> shops = shopRepository.search(keyword);
                shops = findDupes(shops, shopRepository.searchThroughTag(keyword));
                shops = findDupes(shops, shopRepository.searchThroughCategory(keyword));
                return shops;
            }
        }
        return shopRepository.findAll();
    }

    /**
     * Function to add shops from l2 that does not exist in l1
     *
     * @param l1 main shop list
     * @param l2 list of shops to add
     * @return resulting shop list
     */
    public List findDupes(List<Shop> l1, List<Shop> l2){
        for(Shop s: l2){
            if(!l1.contains(s)){
                l1.add(s);
            }
        }
        return l1;
    }
}
