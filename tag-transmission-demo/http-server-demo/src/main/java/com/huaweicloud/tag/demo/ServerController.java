package com.huaweicloud.tag.demo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * ServerController
 *
 * @author lilai
 * @since 2023-09-23
 */
@RestController
public class ServerController {
    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        return "Hello World! This is HTTP server. Received Traffic Tag is [ id : " + request.getHeader("id") + "]";
    }
}
