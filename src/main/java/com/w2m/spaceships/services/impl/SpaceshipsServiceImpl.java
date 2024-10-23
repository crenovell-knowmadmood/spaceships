package com.w2m.spaceships.services.impl;

import static com.w2m.spaceships.constants.EventConstants.CREATE;
import static com.w2m.spaceships.constants.EventConstants.DELETE;
import static com.w2m.spaceships.constants.EventConstants.UPDATE;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import com.w2m.spaceships.kafka.messages.SpaceshipMessageKey;
import com.w2m.spaceships.kafka.messages.SpaceshipMessagePayload;
import com.w2m.spaceships.mappers.SpaceshipMessageMapper;
import com.w2m.spaceships.repositories.SpaceshipRepository;
import com.w2m.spaceships.services.SpaceshipsService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SpaceshipsServiceImpl implements SpaceshipsService {

  @Value("${spring.kafka.producer.topic}")
  String outputTopic;
  @Autowired
  SpaceshipRepository repository;

  @Autowired
  KafkaTemplate kafkaTemplate;
  @Autowired
  SpaceshipMessageMapper mapper;


  @Override
  public Page<Spaceship> getAllSpaceShips(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public Spaceship getSpaceShipById(Integer id) throws SpaceShipNotFoundException {
    Optional<Spaceship> optionalSpaceship = repository.findById(id);

    return optionalSpaceship
        .orElseThrow(() -> new SpaceShipNotFoundException(String.format("SpaceShip with ID %d not found.", id)));
  }

  @Override
  public List<Spaceship> searchSpaceShipsByName(String name) {
    return repository.findByNameContaining(name);
  }

  @Override
  public Spaceship create(final Spaceship spaceShip) {
    final Spaceship spaceship = repository.save(spaceShip);
    SpaceshipMessageKey key = mapper.toMessageKey(spaceship);
    final SpaceshipMessagePayload payload = mapper.toMessagePayload(spaceship, CREATE);
    kafkaTemplate.send(outputTopic, key, payload);
    return spaceship;
  }

  @Override
  public Spaceship update(Integer id, Spaceship spaceShip) throws SpaceShipNotFoundException {
    Spaceship existingSpaceShip = getSpaceShipById(id);
    existingSpaceShip.setName(spaceShip.getName());
    existingSpaceShip.setType(spaceShip.getType());
    final Spaceship spaceship = repository.save(existingSpaceShip);
    SpaceshipMessageKey key = mapper.toMessageKey(existingSpaceShip);
    final SpaceshipMessagePayload payload = mapper.toMessagePayload(spaceship, UPDATE);
    kafkaTemplate.send(outputTopic, key, payload);

    return spaceship;
  }

  @Override
  public void delete(Integer id) throws SpaceShipNotFoundException {
    Spaceship spaceship = getSpaceShipById(id);
    repository.delete(spaceship);
    SpaceshipMessageKey key = mapper.toMessageKey(spaceship);
    final SpaceshipMessagePayload payload = mapper.toMessagePayload(spaceship, DELETE);
    kafkaTemplate.send(outputTopic, key, payload);
  }
}
