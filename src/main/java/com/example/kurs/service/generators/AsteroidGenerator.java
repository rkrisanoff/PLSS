package com.example.kurs.service.generators;

import com.example.kurs.entity.Asteroid;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class AsteroidGenerator {
    private final String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
    public Asteroid generate(){
        String randomName = generateString(characters, 10);
        int distance = ThreadLocalRandom.current().nextInt(1, 1000);
        Asteroid asteroid = new Asteroid();
        asteroid.setDistance(distance);
        asteroid.setName(randomName);
        return asteroid;
    }
    private static String generateString(String characters, int length)
    {
        char[] text = new char[length];
        for (int i = 0; i < length; i++)
        {
            text[i] = characters.charAt(ThreadLocalRandom.current().nextInt(characters.length()));
        }
        return new String(text);
    }
}

