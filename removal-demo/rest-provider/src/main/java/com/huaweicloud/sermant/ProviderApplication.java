/*
 * Copyright (C) 2023-2023 Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.huaweicloud.sermant;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * 离群实例摘除插件示例
 *
 * @author zhp
 * @since 2023-04-14
 */
@SpringBootApplication
@RestController
public class ProviderApplication {
    @Value("${server.port}")
    int port;

    @Value("${timeout}")
    private int timeout;

    /**
     * main方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class);
    }

    /**
     * 测试接口
     *
     * @return 端口信息
     * @throws RuntimeException 中断异常
     */
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        try {
            Thread.sleep(timeout);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "Hello, I am zk rest template provider, my port is " + port;
    }
}
