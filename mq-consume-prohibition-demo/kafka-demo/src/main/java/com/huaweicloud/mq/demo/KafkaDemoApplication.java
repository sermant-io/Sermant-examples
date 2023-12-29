package com.huaweicloud.mq.demo;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Collections;

/**
 * 启动类
 *
 * @author lilai
 * @since 2023-12-25
 */
@SpringBootApplication
public class KafkaDemoApplication {
    public static void main(String[] args) throws InterruptedException {
        SpringApplication.run(KafkaDemoApplication.class, args);
        KafkaConsumer<String, String> consumer = KafkaConsumerManager.getInstance().getConsumer();
        consumer.subscribe(Collections.singleton("demo-test-topic"));
        while (true) {
            consumer.poll(2000);
            Thread.sleep(2000);
        }
    }
}
