package com.example.kurs.RabbitEmailAlert.service;

import com.example.kurs.RabbitEmailAlert.DTO.Message;
import com.example.kurs.RabbitEmailAlert.config.RabbitConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

public class RabbitService {
    @Autowired
    RabbitTemplate template;

    public void sendMailStatusChangeRecipe(Message message) {
        template.setExchange(RabbitConfiguration.EXCHANGE_NAME);
        template.convertAndSend(message);
    }


}
