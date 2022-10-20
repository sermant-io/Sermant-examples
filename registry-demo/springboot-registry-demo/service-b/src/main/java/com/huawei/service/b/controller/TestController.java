/*
 * Copyright (C) 2022-2022 Huawei Technologies Co., Ltd. All rights reserved.
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

package com.huawei.service.b.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

/**
 * 测试
 *
 * @author zhouss
 * @since 2022-10-13
 */
@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private static final long SLEEP_MS = 2000L;

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${server.port}")
    private int port;

    @Value("${config.sleepMs}")
    private int sleepMs;

    @GetMapping("/get")
    public String get(@RequestHeader(value = "input", required = false) String input)
        throws InterruptedException {
        Thread.sleep(SLEEP_MS);
        LOGGER.info("======input: {} ==========", input == null ? "sermant" : input);
        return buildMsg("get");
    }

    @PostMapping("/post")
    public String post(@RequestHeader(value = "input", required = false) String input) {
        LOGGER.info("======input: {} ==========", input == null ? "sermant" : input);
        return buildMsg("post");
    }

    @GetMapping("/retry")
    public String retry() throws InterruptedException {
        Thread.sleep(sleepMs);
        return buildMsg("retry");
    }

    private String buildMsg(String method) {
        return String.format(Locale.ENGLISH, "I am service %s and my port is %s and method"
            + " is %s", serviceName, port, method);
    }

    @GetMapping("weightResponse")
    public String weightResponse() throws InterruptedException {
        Thread.sleep(sleepMs);
        return String.valueOf(port);
    }
}
