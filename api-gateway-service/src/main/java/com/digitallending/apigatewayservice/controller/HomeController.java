package com.digitallending.apigatewayservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HomeController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("home")
    public String homePage()
    {
        return restTemplate.getForObject("http://localhost:8080/home", String.class);
    }

    @RequestMapping("admin")
    public String adminPage()
    {
        return restTemplate.getForObject("http://localhost:8080/admin", String.class);
    }

    @RequestMapping("user")
    public String userPage()
    {
        return  restTemplate.getForObject("http://localhost:8080/user", String.class);
    }

    @RequestMapping("both")
    public String userAndAdminPage()
    {
        return restTemplate.getForObject("http://localhost:8080/both", String.class);
    }

}
