package com.w2m.spaceships.services.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.w2m.spaceships.constants.EventConstants;
import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import com.w2m.spaceships.kafka.messages.SpaceshipMessageKey;
import com.w2m.spaceships.kafka.messages.SpaceshipMessagePayload;
import com.w2m.spaceships.mappers.SpaceshipMessageMapper;
import com.w2m.spaceships.repositories.SpaceshipRepository;
import com.w2m.spaceships.services.KafkaProducerService;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class SpaceshipsServiceTest {

  public static final int ID_SPACESHIP = 2;
  @InjectMocks
  SpaceshipsServiceImpl service;

  @Mock
  SpaceshipRepository repository;

  @Mock
  KafkaProducerService kafkaProducerService;

  @Mock
  SpaceshipMessageMapper mapper;


  @Test
  @Order(1)
  void create() {
    ReflectionTestUtils.setField(service, "isKafkaEnabled", true);
    Spaceship spaceship = createSpaceship(ID_SPACESHIP, "type", "Name");
    when(repository.save(any())).thenReturn(spaceship);
    Supplier<Message<SpaceshipMessagePayload>> sp = createSupplier(spaceship, EventConstants.CREATE);
    when(kafkaProducerService.sendMessage(any(Spaceship.class), anyString())).thenReturn(sp);
    final Spaceship actual = service.create(spaceship);
    verify(repository, times(1)).save(any());
    verify(kafkaProducerService, times(1)).sendMessage(any(Spaceship.class), anyString());
    assertNotNull(actual);

  }

  @Test
  @Order(2)
  void getSpaceShipById() throws SpaceShipNotFoundException {
    Spaceship entity = createSpaceship(ID_SPACESHIP, "tipo", "nombre");
    when(repository.findById(ID_SPACESHIP)).thenReturn(Optional.of(entity));
    Spaceship actual = service.getSpaceShipById(ID_SPACESHIP);
    assertNotNull(actual);
    verify(repository, times(1)).findById(anyInt());
  }

  @Test
  @Order(3)
  void searchSpaceShipsByName() {
    List<Spaceship> spaceships = service.searchSpaceShipsByName("Prueba");
    verify(repository, times(1)).findByNameContaining(anyString());
  }

  @Test
  @Order(4)
  void update() throws SpaceShipNotFoundException {
    Spaceship spaceship = new Spaceship();
    spaceship.setId(ID_SPACESHIP);
    String newType = "changedtype";
    spaceship.setType(newType);
    String newName = "Other name";
    spaceship.setName(newName);
    Spaceship entity = createSpaceship(ID_SPACESHIP, "Tipo", "name");
    when(repository.findById(ID_SPACESHIP)).thenReturn(Optional.of(entity));
    when(repository.save(any(Spaceship.class))).thenReturn(entity);
    Spaceship updatedSpaceship = service.update(ID_SPACESHIP, spaceship);
    assertNotNull(updatedSpaceship);
    assertEquals(newName, updatedSpaceship.getName());
    assertEquals(newType, updatedSpaceship.getType());
  }

  @Test
  @Order(5)
  void delete() throws SpaceShipNotFoundException {
    Spaceship entity = createSpaceship(ID_SPACESHIP, "Tipo", "name");
    doNothing().when(repository).delete(entity);
    when(repository.findById(ID_SPACESHIP)).thenReturn(Optional.of(entity));
    service.delete(ID_SPACESHIP);

  }

  private static Spaceship createSpaceship(int id, String type, String name) {
    Spaceship entity = new Spaceship();
    entity.setId(id);
    entity.setType(type);
    entity.setName(name);
    return entity;
  }

  private Supplier<Message<SpaceshipMessagePayload>> createSupplier(Spaceship spaceship, String action) {
    return () -> {
      SpaceshipMessagePayload p = new SpaceshipMessagePayload();
      SpaceshipMessageKey k = new SpaceshipMessageKey();
      return MessageBuilder
          .withPayload(p)
          .setHeader("key", k)
          .build();
    };
  }
}