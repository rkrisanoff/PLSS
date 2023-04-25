package com.example.kurs.RabbitEmailAlert.config;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.handler.annotation.Header;


@Configuration
public class MqttRabbitMQConfig {

    private final String MQTT_BROKER_URL;

    public MqttRabbitMQConfig(@Value("${app.mqtt-broker-url}") String mqttBrokerUrl) {
        this.MQTT_BROKER_URL = mqttBrokerUrl;
    }

    @Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[] {MQTT_BROKER_URL});

        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MessageHandler mqttOutbound() {
        MqttPahoMessageHandler messageHandler =
                new MqttPahoMessageHandler("example_data_sender_client", mqttClientFactory());
        messageHandler.setAsync(true);
        messageHandler.setDefaultTopic("exampleQueue");
        return messageHandler;
    }
    @Bean
    public Queue createQueue() {
        return new Queue("exampleQueue");
    }

    @Bean
    public Binding createBindingBetweenQueueAndMqttTopic() {
        return new Binding("exampleQueue", Binding.DestinationType.QUEUE, "amq.topic", "bindingKey", null);
    }
    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    public interface MqttDataSenderGateway {
        void sendToMqtt(String data, @Header(MqttHeaders.TOPIC) String topic);
    }
}
