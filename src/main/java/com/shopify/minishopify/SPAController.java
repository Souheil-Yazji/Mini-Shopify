package com.shopify.minishopify;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * This is the SPA controller that will return index.html for requests made to /app/**.
 * All frontend routing should have base path /app.
 */
@Controller
public class SPAController {

    @GetMapping("/app/**")
    public String helloWorld() {
        return "index";
    }
}
