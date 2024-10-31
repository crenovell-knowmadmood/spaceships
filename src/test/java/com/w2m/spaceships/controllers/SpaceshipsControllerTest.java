package com.w2m.spaceships.controllers;

import static com.w2m.spaceships.constants.MappingConstants.CREATE_URL;
import static com.w2m.spaceships.constants.MappingConstants.DELETE_URL;
import static com.w2m.spaceships.constants.MappingConstants.GET_ALL_URL;
import static com.w2m.spaceships.constants.MappingConstants.GET_BY_ID_URL;
import static java.util.Objects.nonNull;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.w2m.spaceships.entities.Spaceship;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


@SpringBootTest
@ActiveProfiles("test")
//@Transactional
//@ExtendWith({SpringExtension.class})
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class SpaceshipsControllerTest extends MyTestUtils<Spaceship> {

  public static final int ID = 1;
  @Autowired
  private MockMvc mockMvc;


  @Value("${spring.application.isKafkaEnabled}")
  boolean isKafkaEnabled;

  @Test
  @Order(1)
  void testCreate() throws Exception {
    final Spaceship expected = createSpaceship(ID, "type", "Name");
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
  void testGetAll() throws Exception {

    mockMvc.perform(get(GET_ALL_URL))
        .andExpect(status().isOk())
        .andExpect(result -> nonNull(result.getResponse()))
        .andExpect(jsonPath("$.totalElements", greaterThan(0)))
    ;
  }

  @Test
  @Order(3)
  void testGetById() throws Exception {

    final MvcResult mvcResult = mockMvc.perform(get(GET_BY_ID_URL, ID))
        .andExpect(status().isOk())
        .andExpect(result -> nonNull(result.getResponse()))
        .andReturn();
    assertNotNull(mvcResult);

  }

  @Test
  @Order(4)
  void testDelete() throws Exception {
    final int id = ID;
    mockMvc.perform(MockMvcRequestBuilders.delete(DELETE_URL, id))
        .andExpect(status().is2xxSuccessful())
    ;
  }


}