package com.tecsup.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {

    // Endpoint público (Caso 1)
    @GetMapping("/all")
    public String allAccess() {
        return "Public Content";
    }

    // Endpoint protegido para roles USER y ADMIN (Casos 2, 3 y 4)
    @GetMapping("/user")
    public String userAccess() {
        return "User Content";
    }

    // Endpoint restringido exclusivo para rol ADMIN (Casos 5 y 6)
    @GetMapping("/admin")
    public String adminAccess() {
        return "Admin Content";
    }

    // Endpoint restringido para rol MODERATOR
    @GetMapping("/mod")
    public String moderatorAccess() {
        return "Moderator Content";
    }
}
