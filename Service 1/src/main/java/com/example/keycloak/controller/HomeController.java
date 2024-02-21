package com.example.keycloak.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("home")
    public String homePage()
    {
        return "Home";
    }

    @RequestMapping("admin")
    public String adminPage()
    {
        return "Admin";
    }

    @RequestMapping("user")
    public String userPage()
    {
        return "User";
    }

    @RequestMapping("both")
    public String userAndAdminPage()
    {
        return "Admin and User";
    }

}
