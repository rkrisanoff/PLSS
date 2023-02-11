package com.example.kurs.service;

import com.example.kurs.dto.RecipeDto;
import com.example.kurs.entity.Kitchen;
import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import com.example.kurs.repo.RecipeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import javax.xml.crypto.dsig.keyinfo.KeyInfo;
import java.lang.reflect.Array;
import java.util.*;

@Slf4j
@Service
public class RecipeService {
    @Autowired
    private RecipeRepo recipeRepo;

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
                recipeRepo.save(recipe);
                return true;
            }
    }


    public List<Recipe> getAll(){
        return (List<Recipe>) recipeRepo.findAll();
    }

    public void changeStatus(Long id, Status status){
        Optional<Recipe> recipe = recipeRepo.findById(id);
        if (!recipe.isPresent()){
            log.info("Recipe with id {} not found.", id);
            return;
        }
        log.info("Found recipe with id {}.", id);
        recipe.get().setStatus(status);
        recipeRepo.save(recipe.get());
    }
}
