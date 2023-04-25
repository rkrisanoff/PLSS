package com.example.kurs.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class FavoriteRecipeRequestDto {
    @NotNull(message = "title must not be empty")
    private Long recipeId;

}
