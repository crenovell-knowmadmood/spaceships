package com.w2m.spaceships.services.impl;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import com.w2m.spaceships.kafka.messages.SpaceshipMessageKey;
import com.w2m.spaceships.kafka.messages.SpaceshipMessagePayload;
import com.w2m.spaceships.mappers.SpaceshipMessageMapper;
import com.w2m.spaceships.repositories.SpaceshipRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;

@ExtendWith(MockitoExtension.class)
class SpaceshipsServiceTest {

  @InjectMocks
  SpaceshipsServiceImpl service;

  @Mock
  SpaceshipRepository repository;

  @Mock
  KafkaTemplate kafkaTemplate;

  @Mock
  SpaceshipMessageMapper mapper;


//  @Test
//  void getAllSpaceShips() {
//    Pageable pageable = Pageable.ofSize(2);
//
//    when(repository.findAll(pageable)).thenReturn()
//    Page<Spaceship> allSpaceShips = service.getAllSpaceShips(pageable);
//  }

  @Test
  void getSpaceShipById() throws SpaceShipNotFoundException {
    final int id = 2;
    Spaceship entity = createSpaceship(2, "tipo", "nombre");
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    Spaceship actual = service.getSpaceShipById(id);
    assertNotNull(actual);
    verify(repository, times(1)).findById(anyInt());
  }

  @Test
  void searchSpaceShipsByName() {
    List<Spaceship> spaceships = service.searchSpaceShipsByName("Prueba");
    verify(repository, times(1)).findByNameContaining(anyString());
  }

  @Test
  void create() {
    Spaceship spaceship = createSpaceship(2,"type","Name");
    when(repository.save(any())).thenReturn(spaceship);
    when(mapper.toMessageKey(any())).thenReturn(new SpaceshipMessageKey()); // Mock del mapper
    when(mapper.toMessagePayload(any(), any())).thenReturn(new SpaceshipMessagePayload()); // Mock del payload

    final Spaceship actual = service.create(spaceship);
    verify(repository, times(1)).save(any());
    verify(kafkaTemplate, times(1)).send(any(), any(), any());
    assertNotNull(actual);

  }

  @Test
  void update() {
  }

  @Test
  void delete() throws SpaceShipNotFoundException {
    int id = 2;
    Spaceship entity = createSpaceship(id, "Tipo", "name");
    doNothing().when(repository).delete(entity);
    when(repository.findById(id)).thenReturn(Optional.of(entity));
    service.delete(id);

  }

  private static Spaceship createSpaceship(int id, String type, String name) {
    Spaceship entity = new Spaceship();
    entity.setId(id);
    entity.setType(type);
    entity.setName(name);
    return entity;
  }
}