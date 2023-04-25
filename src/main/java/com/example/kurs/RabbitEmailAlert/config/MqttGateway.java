package com.example.kurs.RabbitEmailAlert.config;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.handler.annotation.Header;

@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttGateway {

  void sendToMqtt(
          @Header(MqttHeaders.TOPIC) String topic,
          @Header(MqttHeaders.RETAINED) boolean isRetained,
          String payload);
}
