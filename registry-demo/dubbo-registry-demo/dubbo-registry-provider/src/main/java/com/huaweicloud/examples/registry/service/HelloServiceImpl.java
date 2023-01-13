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

package com.huaweicloud.examples.registry.service;

import com.huaweicloud.examples.registry.api.HelloService;

import org.apache.dubbo.config.RegistryConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

/**
 * 测试接口
 *
 * @author provenceee
 * @since 2022-08-29
 */
public class HelloServiceImpl implements HelloService {
    @Autowired
    private RegistryConfig registryConfig;

    @Value("${dubbo.application.name}")
    private String name;

    @Value("${dubbo.protocol.port}")
    private String port;

    @Override
    public String hello() {
        return "I'm " + name + ", my registry center is " + registryConfig.getProtocol() + ", my port is " + port + ".";
    }
}