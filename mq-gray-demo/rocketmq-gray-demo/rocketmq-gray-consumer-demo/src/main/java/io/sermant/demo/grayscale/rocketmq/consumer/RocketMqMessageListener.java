/*
 * Copyright (C) 2024-2024 Sermant Authors. All rights reserved.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package io.sermant.demo.grayscale.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * consumer message listener
 *
 * @author chengyouling
 * @since 2024-10-30
 **/
@Component
public class RocketMqMessageListener implements CommandLineRunner {
    private static final Logger LOGGER = LoggerFactory.getLogger(RocketMqMessageListener.class);

    @Value("${rocketmq.address}")
    private String nameServer;

    @Value("${rocketmq.topic}")
    private String topic;

    @Override
    public void run(String... args) throws Exception {
        subscribeMessages();
    }

    private void subscribeMessages() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("default");
        consumer.setNamesrvAddr(nameServer);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
        consumer.subscribe(topic, "*");
        consumer.registerMessageListener((MessageListenerOrderly) (messageExts, context) -> {
            if (messageExts != null) {
                for (MessageExt messageExt : messageExts) {
                    String message = new String(messageExt.getBody(), StandardCharsets.UTF_8);
                    LOGGER.info("sub message: " + message);
                }
            }
            return ConsumeOrderlyStatus.SUCCESS;
        });
        consumer.start();
    }
}
