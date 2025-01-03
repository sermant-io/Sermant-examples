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

package io.sermant.demo.grayscale.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * producer controller
 *
 * @author chengyouling
 * @since 2024-10-30
 */
@RestController
public class MqProducerController {
  private static final Logger LOGGER = LoggerFactory.getLogger(MqProducerController.class);

  @Value("${rocketmq.address}")
  private String mqAddress;

  @Value("${rocketmq.topic}")
  private String mqTopic;

  @Value("${service_meta_version:${SERVICE_META_VERSION:${service.meta.version:1.0.0}}}")
  private String version;

  private DefaultMQProducer defaultMQProducer;

  @GetMapping("/sendMessage")
  public String producerMessage(@RequestParam("message") String message) {
    try {
      if (defaultMQProducer == null) {
        defaultMQProducer = new DefaultMQProducer("default");
        defaultMQProducer.setNamesrvAddr(mqAddress);
        defaultMQProducer.setSendMsgTimeout(60000);
        defaultMQProducer.start();
      }
      String realMessage = "version " + version + " send message " + message;
      Message mqMessage = new Message(mqTopic, realMessage.getBytes());
      defaultMQProducer.send(mqMessage);
    } catch (Exception e) {
      LOGGER.error("send message error, address={}, message={}", mqAddress, message, e);
      return "error";
    }
    return "success";
  }
}
