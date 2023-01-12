package com.cloud.demos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam("name") String name) {
        return restTemplate.getForObject("http://providerA/sayHello?name={1}", String.class, name);
    }
}
