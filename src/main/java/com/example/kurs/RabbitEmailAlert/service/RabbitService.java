package com.example.kurs.RabbitEmailAlert.service;

import com.example.kurs.RabbitEmailAlert.DTO.Message;
import com.example.kurs.RabbitEmailAlert.config.RabbitConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RabbitService {
    @Autowired
    RabbitTemplate template;

    @Value("${rabbitmq.EXCHANGE_NAME}")
    private static String EXCHANGE_NAME;

    public void sendMailStatusChangeRecipe(String message) {
        template.setExchange(EXCHANGE_NAME);
        template.convertAndSend(message);
    }


}
