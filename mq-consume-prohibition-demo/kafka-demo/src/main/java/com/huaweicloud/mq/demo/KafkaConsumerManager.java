package com.huaweicloud.mq.demo;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Properties;

/**
 * KakfaConsumer管理类
 *
 * @author lilai
 * @since 2023-12-25
 */
public class KafkaConsumerManager {
    private final KafkaConsumer<String, String> consumer;

    private static KafkaConsumerManager instance;

    private KafkaConsumerManager() {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "demo-test-group");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        consumer = new KafkaConsumer<>(properties);
    }

    /**
     * 获取kafka消费者管理类实例
     *
     * @return kafka消费者管理实例
     */
    public static synchronized KafkaConsumerManager getInstance() {
        if (instance == null) {
            instance = new KafkaConsumerManager();
        }
        return instance;
    }

    /**
     * 获取消费者
     *
     * @return 消费者
     */
    public KafkaConsumer<String, String> getConsumer() {
        return consumer;
    }
}

