package com.w2m.spaceships.controllers;

import static com.w2m.spaceships.constants.MappingConstants.CREATE_URL;
import static com.w2m.spaceships.constants.MappingConstants.GET_ALL_URL;
import static com.w2m.spaceships.constants.MappingConstants.GET_BY_ID_URL;
import static java.util.Objects.nonNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.w2m.spaceships.entities.Spaceship;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith({SpringExtension.class})
@AutoConfigureMockMvc
class SpaceshipsControllerTest extends MyTestUtils<Spaceship> {

  @Autowired
  private MockMvc mockMvc;


  @Test
  @Order(1)
  void create() throws Exception {
    Spaceship spaceship = new Spaceship();
    spaceship.setId(2);
    spaceship.setType("tipo");
    spaceship.setName("Name");
    final String content = this.writeToJson(spaceship);
    MockHttpServletRequestBuilder requestBuilder = post(CREATE_URL)
        .accept(MediaType.APPLICATION_JSON)
        .content(content).contentType(MediaType.APPLICATION_JSON);
    mockMvc.perform(requestBuilder)
        .andExpect(status().is2xxSuccessful())

    ;
  }


  @Test
  @Order(3)
  void getAll() throws Exception {

    mockMvc.perform(get(GET_ALL_URL))
        .andExpect(status().isOk())
        .andExpect(result -> nonNull(result.getResponse()))
    ;
  }


}