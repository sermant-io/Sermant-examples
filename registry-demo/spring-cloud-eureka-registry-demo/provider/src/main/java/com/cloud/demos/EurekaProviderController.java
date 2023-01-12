package com.cloud.demos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EurekaProviderController {

    @GetMapping("/sayHello")
    public String sayEurekaHello(@RequestParam("name") String name) {
        return "Hello:name---" + name;
    }
}
