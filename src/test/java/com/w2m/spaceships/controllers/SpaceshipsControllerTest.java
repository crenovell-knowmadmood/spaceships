package com.w2m.spaceships.controllers;

import static com.w2m.spaceships.constants.MappingConstants.GET_ALL_URL;
import static com.w2m.spaceships.constants.MappingConstants.GET_BY_ID_URL;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith({SpringExtension.class})
@AutoConfigureMockMvc
class SpaceshipsControllerTest {

  @Autowired
  private MockMvc mockMvc;


  @Test
  void getAll() throws Exception {

    mockMvc.perform(get(GET_ALL_URL))
        .andExpect(status().isOk())
    ;
  }

  @Test
  void getById() throws Exception {
    mockMvc.perform(get(GET_BY_ID_URL))
        .andExpect(status().isOk())
    ;
  }
}