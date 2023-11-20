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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 离群实例摘除插件consumer应用示例
 *
 * @author zhp
 * @since 2023-04-14
 */
@SpringBootApplication
@RestController
public class ConsumerApplication {
    @Autowired
    RestTemplate restTemplate;

    /**
     * main方法
     *
     * @param args 参数
     */
    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class);
    }

    /**
     * 测试接口
     *
     * @return 端口信息
     */
    @GetMapping("/hello")
    public String hello() {
        return restTemplate.getForObject("http://rest-provider/hello", String.class);
    }
}

