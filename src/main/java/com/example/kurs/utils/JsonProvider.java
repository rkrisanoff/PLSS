package com.example.kurs.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.springframework.stereotype.Component;

@Component
public class JsonProvider {
    ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();

    public String convertToJson(Object o) throws JsonProcessingException {
        return writer.writeValueAsString(o);
    }

}
