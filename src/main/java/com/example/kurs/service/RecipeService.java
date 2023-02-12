package com.example.kurs.service;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.entity.Kitchen;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.repo.RecipeRepo;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;
    @Autowired
    private ObjectMapper ObjectMapper;

    public Recipe getById(Long id){
        Optional<Recipe> recipe = recipeRepo.findById(id);
        if (!recipe.isPresent()){
            log.info("Recipe with id {} not found.", id);
            return null;
        }
        log.info("Found recipe with id {}.", id);
        return recipe.get();

    }
    private Boolean checkTitle(String title){
        if(title!=null && title.length()>0 && title.length()<=32){
            return true;
        }else {
            return false;
        }
    }
    private Boolean checkDescription(String description){
        if(description!=null && description.length()>0 && description.length()<=4096){
            return true;
        }else{
            return false;
        }
    }
    private Boolean checkKitchen(String kitchen){
            try{
                Kitchen.valueOf(kitchen);
            }catch (IllegalArgumentException e){
                return false;
            }
            return true;
    }


    private Boolean checkValidRecipeDto(RecipeDto recipeDto){
        String title=recipeDto.getTitle();
        String description=recipeDto.getDescription();
        String kitchen=recipeDto.getKitchen();
        if(checkTitle(title)&& checkDescription(description)&& checkKitchen(kitchen)){
            return true;
        }else{
            return false;
        }
    }
    private Recipe recipeDtoToRecipe(RecipeDto recipeDto){
        Recipe recipe=null;
        if(checkValidRecipeDto(recipeDto)){
            recipe =new Recipe();
            recipe.setTitle(recipeDto.getTitle());
            recipe.setDescription(recipeDto.getDescription());
            recipe.setKitchen(Kitchen.valueOf(recipeDto.getKitchen()));
        }
        return recipe;
    }

    public Boolean pushNewResive(RecipeDto recipeDto, Long id){
            Recipe recipe=recipeDtoToRecipe(recipeDto);
            if(recipe.equals(null)){
                return false;
            }else{
                recipe.setAuthorId(id);
                recipe.setStatus(Status.MODERATION);
                recipeRepo.save(recipe);
                return true;
            }
    }


   public String getAllRecipeOnModeration(){
        String stringForReturn=null;
        List<Recipe> entitys=recipeRepo.findByStatus(Status.MODERATION.getName());
        try {
            stringForReturn=ObjectMapper.writeValueAsString(entitys);
        }catch (JsonProcessingException e){
            return stringForReturn;
        }

        return stringForReturn;
   }
   public String getRecipeOnModerationId(Long id){
       String stringForReturn=null;
       Optional<Recipe> entity=recipeRepo.findById(id);
       try {
           stringForReturn=ObjectMapper.writeValueAsString(entity);
       }catch (JsonProcessingException e){
           return stringForReturn;
       }

       return stringForReturn;
   }

    public Integer changeStatus(Long id, String status){
        return recipeRepo.setStatusForRecipe(status,id);
    }
}
