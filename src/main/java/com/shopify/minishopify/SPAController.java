package com.shopify.minishopify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/app/**")
    public String helloWorld() {
        return "index";
    }
}
