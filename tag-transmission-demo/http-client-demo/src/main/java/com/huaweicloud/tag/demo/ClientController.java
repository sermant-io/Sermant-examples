package com.huaweicloud.tag.demo;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * ClientController
 *
 * @author lilai
 * @since 2023-09-23
 */
@RestController
public class ClientController {
    @Value("${http.server.ip}")
    private String ip;

    @Value("${http.server.port}")
    private int port;

    @GetMapping("/testHttpClient4")
    public String testHttpClient4() throws IOException {
        // 创建HttpClient实例
        CloseableHttpClient httpClient = HttpClients.createDefault();

        // 创建HTTP GET请求
        HttpGet httpGet = new HttpGet("http://" + ip + ":" + port + "/hello");

        // 执行请求并获取响应
        CloseableHttpResponse response = httpClient.execute(httpGet);

        // 获取响应实体
        HttpEntity entity = response.getEntity();

        return EntityUtils.toString(entity);
    }
}
