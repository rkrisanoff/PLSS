package com.example.kurs.repo;

import com.example.kurs.entity.Recipe;
import com.example.kurs.entity.Status;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface RecipeRepo extends PagingAndSortingRepository<Recipe, Long> {
    Recipe save(Recipe recipe);

    @NotNull
    Optional<Recipe> findById(@NotNull Long id);


    Page<Recipe> findAllByStatus(Status status, Pageable pageable);

    @Modifying
    @Query("update Recipe recipe set recipe.status = ?1 where recipe.id = ?2")
    int setStatusForRecipe(Status status, Long id);



}
