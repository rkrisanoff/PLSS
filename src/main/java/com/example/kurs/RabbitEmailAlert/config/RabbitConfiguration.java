package com.example.kurs.RabbitEmailAlert.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {
    @Value("${rabbitmq.QUEUE_FOR_EMAIL}")
    private static String QUEUE_FOR_EMAIL;
    @Value("${rabbitmq.EXCHANGE_NAME}")
    private static String EXCHANGE_NAME;
    @Value("${rabbitmq.ROUTING_KEY}")
    private static String ROUTING_KEY;
    @Value("${rabbitmq.HOST}")
    private static String HOST;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(HOST);
        return connectionFactory;
    }

    @Bean
    public FanoutExchange fanoutExchangeA() {
        return new FanoutExchange(EXCHANGE_NAME);
    }

    @Bean
    public Binding binding1() {
        return BindingBuilder.bind(queueForEmail()).to(fanoutExchangeA());
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue queueForEmail() {
        return new Queue(QUEUE_FOR_EMAIL);
    }
}
