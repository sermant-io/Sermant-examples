package com.cloud.demos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class ProviderController {

    @Value("${SERVICE_META_VERSION:${service_meta_version:${service.meta.version:1.0.0}}}")
    private String version;

    @Value("${SERVICE_META_PARAMETERS:${service_meta_parameters:${service.meta.parameters:base}}}")
    private String parameters;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/sayHello")
    public String sayHello(@RequestParam("name") String name) {
        String message = "ProviderA-gray:name---" + name + "---version----" + version + "-------parameters----" + parameters;
        System.out.println(message);
        return message + restTemplate.getForObject("http://providerB/sayHello?name={1}", String.class, name);
    }
}
