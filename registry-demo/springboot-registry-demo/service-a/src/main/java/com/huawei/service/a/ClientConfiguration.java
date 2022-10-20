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

package com.huawei.service.a;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.SystemDefaultHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.client.HttpAsyncClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 配置类
 *
 * @author zhouss
 * @since 2022-10-13
 */
@Configuration
public class ClientConfiguration {
    private static final int DEFAULT_CONNECT_TIMEOUT = 1000;
    @Value("${config.timeout}")
    private int timeout;

    @Bean(name = "defaultHttpClient")
    public HttpClient defaultHttpClient() {
        return new SystemDefaultHttpClient();
    }

    @Bean(name = "minimalHttpClient")
    public HttpClient minimalHttpClient() {
        return HttpClients.createMinimal();
    }

    @Bean(name = "httpClient")
    public HttpClient httpClient() {
        return HttpClients.custom()
            .setDefaultRequestConfig(RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(timeout)
                .build())
            .build();
    }

    @Bean(name = "minimalHttpAsyncClient")
    public HttpAsyncClient minimalHttpAsyncClient() {
        final CloseableHttpAsyncClient minimal = HttpAsyncClients.createMinimal();
        minimal.start();
        return minimal;
    }

    @Bean(name = "internalHttpAsyncClient")
    public HttpAsyncClient internalHttpAsyncClient() {
        final CloseableHttpAsyncClient asyncClient = HttpAsyncClients.custom().setDefaultRequestConfig(
            RequestConfig.custom()
                .setConnectTimeout(DEFAULT_CONNECT_TIMEOUT)
                .setSocketTimeout(timeout)
                .build()).build();
        asyncClient.start();
        return asyncClient;
    }
}
