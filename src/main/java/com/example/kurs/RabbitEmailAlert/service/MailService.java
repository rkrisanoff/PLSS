package com.example.kurs.RabbitEmailAlert.service;

import com.example.kurs.RabbitEmailAlert.DTO.Message;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.User;
import com.example.kurs.exceptions.UserNotFoundException;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;
    @Autowired
    RabbitService rabbitService;
    @Autowired
    ObjectMapper objectMapper;

    public void statusСhangeRecipeEmailAlert(Recipe recipe) throws UserNotFoundException, JsonProcessingException {
        Message message = makeMessageStatusСhangeRecipe(recipe);
        String jsonForSend= messageToJson(message);
        rabbitService.sendMailStatusChangeRecipe(jsonForSend);

    }


    private String messageToJson(Message message) throws JsonProcessingException {
        String jsonString = objectMapper.writeValueAsString(message);
        return jsonString;
    }

    private Message makeMessageStatusСhangeRecipe(Recipe recipe) throws UserNotFoundException {
        User user = userService.getUser(recipe.getAuthorId());

        String subject = String.format("Смена статуса у рецепта: %s", recipe.getTitle());
        String emailAddres = user.getEmail();
        String text = String.format("Привет %s! Мы отправляем это письмо, " +
                "чтоб оповестить тебя о том, что твоей рецепт %s был" +
                " проверен админестратором и ему был установлен статус %s", user.getUsername(), recipe.getTitle(), recipe.getStatus());
        return new Message(subject, emailAddres, text);
    }


}
