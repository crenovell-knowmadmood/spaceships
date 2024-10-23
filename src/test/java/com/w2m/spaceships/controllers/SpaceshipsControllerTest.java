package com.w2m.spaceships.controllers;

import static com.w2m.spaceships.constants.MappingConstants.CREATE_URL;
import static com.w2m.spaceships.constants.MappingConstants.DELETE_URL;
import static com.w2m.spaceships.constants.MappingConstants.GET_ALL_URL;
import static com.w2m.spaceships.constants.MappingConstants.GET_BY_ID_URL;
import static java.util.Objects.nonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.w2m.spaceships.entities.Spaceship;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
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
    final int id = 2;
    Spaceship expected = createSpaceship(id, "type", "Name");
    final String content = this.writeToJsonString(expected);
    final MockHttpServletRequestBuilder requestBuilder = post(CREATE_URL)
        .accept(MediaType.APPLICATION_JSON)
        .content(content).contentType(MediaType.APPLICATION_JSON);
    final MvcResult result = mockMvc.perform(requestBuilder)
        .andExpect(status().is2xxSuccessful())
        .andReturn();
    final MockHttpServletResponse response = result.getResponse();
    final String contentAsString = response.getContentAsString();
    final Spaceship actual = this.jsonStringToObject(contentAsString, Spaceship.class);
    assertNotNull(actual);
    assertNotNull(actual.getId());

    assertEquals(expected.getType(), actual.getType());
    assertEquals(expected.getName(), actual.getName());

  }


  @Test
  @Order(2)
  void getAll() throws Exception {

    mockMvc.perform(get(GET_ALL_URL))
        .andExpect(status().isOk())
        .andExpect(result -> nonNull(result.getResponse()))
    ;
  }

  @Test
  @Order(3)
  void getById() throws Exception {
    final int id = 2;
    MvcResult actual = mockMvc.perform(get(GET_BY_ID_URL, id))
        .andExpect(status().isOk())
        .andExpect(result -> nonNull(result.getResponse())).andReturn();
    assertNotNull(actual);
    assertNotNull(actual.getResponse());
    String result = actual.getResponse().getContentAsString();
    assertNotNull(result);
    Spaceship c = this.jsonStringToObject(result, Spaceship.class);
  }

  @Test
  @Order(4)
  void delete() throws Exception {
    final int id = 2;
    mockMvc.perform(get(DELETE_URL, id))
        .andExpect(status().is2xxSuccessful())
    ;
  }


  @NotNull
  private static Spaceship createSpaceship(final Integer id, final String type, final String name) {
    Spaceship spaceship = new Spaceship();
    spaceship.setId(id);
    spaceship.setType(type);
    spaceship.setName(name);
    return spaceship;
  }


}