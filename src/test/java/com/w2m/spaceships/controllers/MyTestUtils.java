package com.w2m.spaceships.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MyTestUtils<T> {
  protected String writeToJsonString(T entity) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.writeValueAsString((T) entity);
  }
  protected <T> T jsonStringToObject(final String content, Class<?> cl) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    T value = (T) objectMapper.readValue(content, cl);
    return  value;
  }

}
