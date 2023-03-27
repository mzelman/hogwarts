package com.mz.hogwarts.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import lombok.AllArgsConstructor;

@AllArgsConstructor
@Controller
public class IndexController {
    
    @GetMapping("/")
    public String mainPage() {
        return "index";
    }
}
