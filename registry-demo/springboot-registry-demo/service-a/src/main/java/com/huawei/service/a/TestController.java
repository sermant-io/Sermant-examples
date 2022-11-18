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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicReference;

import javax.annotation.Resource;

/**
 * 测试
 *
 * @author zhouss
 * @since 2022-10-13
 */
@RestController
public class TestController {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestController.class);

    private static final String GET_METHOD = "get";

    private static final String POST_METHOD = "post";

    @Resource(name = "defaultHttpClient")
    private HttpClient defaultHttpClient;

    @Resource(name = "minimalHttpClient")
    private HttpClient minimalHttpClient;

    @Resource(name = "httpClient")
    private HttpClient httpClient;

    @Resource(name = "minimalHttpAsyncClient")
    private HttpAsyncClient minimalHttpAsyncClient;

    @Resource(name = "internalHttpAsyncClient")
    private HttpAsyncClient internalHttpAsyncClient;

    @Value("${config.domain}")
    private String domain;

    @Value("${config.futureTimeout}")
    private int futureTimeout;

    @Value("${config.downStreamB}")
    private String downStreamB;

    @Value("${config.downStreamC}")
    private String downStreamC;

    @Value("${config.gateWayPort}")
    private int gateWayPort;

    private final Map<String, Long> counter = new HashMap<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(10, r -> new Thread(r, "async"));

    @GetMapping("weightResponse")
    public String weightResponse() throws IOException {
        final String port = invokeHttpClient(httpClient, new HttpGet(buildUrl("weightResponse")));
        Long count = counter.getOrDefault(port, 0L);
        count++;
        counter.put(port, count);
        return counter.toString();
    }

    @GetMapping("resetCounter")
    public String resetCounter() {
        counter.clear();
        return "ok";
    }

    @GetMapping("hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("defaultHttpClientGet")
    public String defaultHttpClientGet() throws IOException {
        return invokeHttpClient(defaultHttpClient, new HttpGet(buildUrl(GET_METHOD)));
    }

    @GetMapping("defaultHttpClientPost")
    public String defaultHttpClientPost() throws IOException {
        return invokeHttpClient(defaultHttpClient, new HttpPost(buildUrl(POST_METHOD)));
    }

    @GetMapping("minHttpClientGet")
    public String minHttpClientGet() throws IOException {
        return invokeHttpClient(minimalHttpClient, new HttpGet(buildUrl(GET_METHOD)));
    }

    @GetMapping("minHttpClientPost")
    public String minHttpClientPost() throws IOException {
        return invokeHttpClient(minimalHttpClient, new HttpPost(buildUrl(POST_METHOD)));
    }

    @GetMapping("httpClientGet")
    public String httpClientGet() throws IOException {
        return invokeHttpClient(httpClient, new HttpGet(buildUrl(GET_METHOD)));
    }

    @GetMapping("httpClientPost")
    public String httpClientPost() throws IOException {
        return invokeHttpClient(httpClient, new HttpPost(buildUrl(POST_METHOD)));
    }

    @GetMapping("httpClientRetry")
    public String httpClientRetry() throws IOException {
        return invokeHttpClient(httpClient, new HttpGet(buildUrl("retry")));
    }

    @GetMapping("gateWayB")
    public String gateWayB() throws IOException {
        return invokeHttpClient(httpClient, new HttpGet(String.format("http://%s:%s/%s/get", domain, gateWayPort,
            downStreamB)));
    }

    @GetMapping("gateWayC")
    public String gateWayC() throws IOException {
        return invokeHttpClient(httpClient, new HttpGet(String.format("http://%s:%s/%s/get", domain, gateWayPort,
            downStreamC)));
    }

    private String invokeHttpClient(HttpClient curHttpClient, HttpRequestBase requestBase) throws IOException {
        LOGGER.info("invoke url: {}", requestBase.getURI());
        HttpResponse response = null;
        try {
            response = curHttpClient.execute(requestBase);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return "failed";
    }

    @GetMapping("httpAsyncClientGet")
    public String httpAsyncClientGet() throws IOException {
        return asyncInvoke(internalHttpAsyncClient, new HttpGet(buildUrl(GET_METHOD)));
    }

    @GetMapping("httpAsyncClientPost")
    public String httpAsyncClientPost() throws IOException {
        return asyncInvoke(internalHttpAsyncClient, new HttpPost(buildUrl(POST_METHOD)));
    }

    @GetMapping("minimalHttpAsyncClientPost")
    public String minimalHttpAsyncClientPost() throws IOException {
        return asyncInvoke(minimalHttpAsyncClient, new HttpPost(buildUrl(POST_METHOD)));
    }

    @GetMapping("minimalHttpAsyncClientGet")
    public String minimalHttpAsyncClientGet() throws IOException {
        return asyncInvoke(minimalHttpAsyncClient, new HttpGet(buildUrl(GET_METHOD)));
    }

    private String asyncInvoke(HttpAsyncClient httpAsyncClient, HttpRequestBase requestBase) throws IOException {
        final Future<HttpResponse> execute = httpAsyncClient.execute(requestBase, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                LOGGER.info(result.toString());
            }

            @Override
            public void failed(Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            @Override
            public void cancelled() {

            }
        });

        HttpResponse response = null;
        try {
            response = execute.get(futureTimeout, TimeUnit.SECONDS);
            return EntityUtils.toString(response.getEntity());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        } finally {
            if (response != null) {
                EntityUtils.consume(response.getEntity());
            }
        }
        return "failed async";
    }

    @GetMapping("minimalHttpAsyncClientThreadGet")
    public String minimalHttpAsyncClientThreadGet() throws IOException {
        return asyncInvokerThread(minimalHttpAsyncClient, new HttpGet(buildUrl(GET_METHOD)));
    }

    @GetMapping("minimalHttpAsyncClientThreadPost")
    public String minimalHttpAsyncClientThreadPost() throws IOException {
        return asyncInvokerThread(minimalHttpAsyncClient, new HttpPost(buildUrl(POST_METHOD)));
    }

    @GetMapping("httpAsyncClientThreadGet")
    public String httpAsyncClientThreadGet() throws IOException {
        return asyncInvokerThread(internalHttpAsyncClient, new HttpGet(buildUrl(GET_METHOD)));
    }

    @GetMapping("httpAsyncClientThreadPost")
    public String httpAsyncClientThreadPost() throws IOException {
        return asyncInvokerThread(internalHttpAsyncClient, new HttpPost(buildUrl(POST_METHOD)));
    }

    private String asyncInvokerThread(HttpAsyncClient httpAsyncClient, HttpRequestBase requestBase) throws IOException {
        final Future<HttpResponse> execute = httpAsyncClient.execute(requestBase, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse result) {
                LOGGER.info(result.toString());
            }

            @Override
            public void failed(Exception ex) {
                LOGGER.error(ex.getMessage(), ex);
            }

            @Override
            public void cancelled() {

            }
        });
        AtomicReference<HttpResponse> response = new AtomicReference<>();
        try {
            final CountDownLatch countDownLatch = new CountDownLatch(1);
            executorService.execute(() -> {
                try {
                    response.set(execute.get(futureTimeout, TimeUnit.SECONDS));
                } catch (InterruptedException | ExecutionException | TimeoutException e) {
                    LOGGER.error(e.getMessage(), e);
                } finally {
                    countDownLatch.countDown();
                }
            });
            countDownLatch.await();
            return EntityUtils.toString(response.get().getEntity());
        } catch (Exception ex) {
            LOGGER.error(ex.getMessage(), ex);
        } finally {
            if (response.get() != null) {
                EntityUtils.consume(response.get().getEntity());
            }
        }
        return "failed thread async";
    }

    private String buildUrl(String path) {
        return String.format(Locale.ENGLISH, "http://%s/%s/%s", domain, downStreamB, path);
    }
}
