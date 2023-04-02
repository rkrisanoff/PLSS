package com.example.kurs.RabbitEmailAlert.service;

import com.example.kurs.RabbitEmailAlert.DTO.Message;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.User;
import com.example.kurs.exceptions.UserNotFoundException;
import com.example.kurs.service.RecipeService;
import com.example.kurs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class MailService {
    @Autowired
    RecipeService recipeService;
    @Autowired
    UserService userService;
    @Autowired
    RabbitService rabbitService;

    public void statusСhangeRecipeEmailAlert(Recipe recipe) throws UserNotFoundException {
        Message message = makeMessageStatusСhangeRecipe(recipe);
        rabbitService.sendMailStatusChangeRecipe(message);

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
