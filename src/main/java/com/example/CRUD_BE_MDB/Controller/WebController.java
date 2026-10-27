package com.example.CRUD_BE_MDB.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/")
    public String index() {
        // Looks for index.html in src/main/resources/templates/index.html
        return "index"; 
    }
}