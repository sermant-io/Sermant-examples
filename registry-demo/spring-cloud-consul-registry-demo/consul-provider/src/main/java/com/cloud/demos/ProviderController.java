package com.cloud.demos;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam("name") String name) {
        return "Hello:name---" + name;
    }
}
