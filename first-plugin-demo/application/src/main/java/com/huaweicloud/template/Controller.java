package com.huaweicloud.template;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务端接口
 *
 * @author daizhenyu
 * @since 2023-10-19
 **/
@RestController
public class Controller {
    @RequestMapping("sayHello")
    public void sayHello(String name) throws InterruptedException {
        System.out.println("hello " + name);
        Thread.sleep(10);
    }
}
