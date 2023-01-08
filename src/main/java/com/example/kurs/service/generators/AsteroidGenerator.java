package com.example.kurs.service.generators;

import com.example.kurs.entity.Asteroid;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class AsteroidGenerator {
    private final String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
    public Asteroid generate(){
        Random rnd = new Random();
        String randomName = generateString(rnd, characters, 10);
        int distance = rnd.nextInt(1, 1000);
        Asteroid asteroid = new Asteroid();
        asteroid.setDistance(distance);
        asteroid.setName(randomName);
        return asteroid;
    }
    private static String generateString(Random rng, String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return new String(text);
    }
}

