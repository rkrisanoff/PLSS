package com.example.kurs.RabbitEmailAlert.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitConfiguration {

    public static final String QUEUE_FOR_EMAIL = "queueForEmail";
    public static final String EXCHANGE_NAME = "exchangeOne";
    public static final String ROUTING_KEY = "recipeStatusUpdate";

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory("localhost");
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
