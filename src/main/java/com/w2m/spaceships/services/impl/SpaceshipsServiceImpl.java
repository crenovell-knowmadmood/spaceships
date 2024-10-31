package com.w2m.spaceships.services.impl;

import static com.w2m.spaceships.constants.EventConstants.CREATE;
import static com.w2m.spaceships.constants.EventConstants.DELETE;
import static com.w2m.spaceships.constants.EventConstants.UPDATE;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import com.w2m.spaceships.mappers.SpaceshipMessageMapper;
import com.w2m.spaceships.repositories.SpaceshipRepository;
import com.w2m.spaceships.services.KafkaProducerService;
import com.w2m.spaceships.services.SpaceshipsService;
import java.util.List;
import java.util.Optional;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SpaceshipsServiceImpl implements SpaceshipsService {

//  @Value("${spring.kafka.producer.topic}")
  @Value("${spring.cloud.stream.bindings.sendMessage-out-0.destination}")
  String outputTopic;
  @Autowired
  SpaceshipRepository repository;

  @Value("${spring.application.isKafkaEnabled}")
  boolean isKafkaEnabled;
  @Autowired
  KafkaProducerService kafkaProducerService;
  private static final Logger logger = LogManager.getLogger(SpaceshipsServiceImpl.class);

  @Override
  public Page<Spaceship> getAllSpaceShips(Pageable pageable) {
    return repository.findAll(pageable);
  }

  @Override
  public Spaceship getSpaceShipById(Integer id) throws SpaceShipNotFoundException {
    Optional<Spaceship> optionalSpaceship = repository.findById(id);

    return optionalSpaceship.orElseThrow(
        () -> new SpaceShipNotFoundException(String.format("SpaceShip with ID %d not found.", id)));
  }

  @Override
  public List<Spaceship> searchSpaceShipsByName(String name) {
    return repository.findByNameContaining(name);
  }

  @Override
  public Spaceship create(final Spaceship spaceShip) {
    final Spaceship spaceship = repository.save(spaceShip);

    if (isKafkaEnabled) {
      logger.debug("Se va a dejar mensaje en el topic: %s", outputTopic);
      kafkaProducerService.sendMessage(spaceship, CREATE);
    }
    return spaceship;
  }

  @Override
  public Spaceship update(Integer id, Spaceship spaceShip) throws SpaceShipNotFoundException {
    Spaceship existingSpaceShip = getSpaceShipById(id);
    if (!spaceShip.getName().equals(existingSpaceShip.getName())) {
      existingSpaceShip.setName(spaceShip.getName());
    }
    if (!existingSpaceShip.getType().equals(spaceShip.getType())) {
      existingSpaceShip.setType(spaceShip.getType());
    }
    final Spaceship spaceship = repository.save(existingSpaceShip);
    if (isKafkaEnabled) {
      kafkaProducerService.sendMessage(spaceship, UPDATE);
    }

    return spaceship;
  }

  @Override
  public void delete(Integer id) throws SpaceShipNotFoundException {
    Spaceship spaceship = getSpaceShipById(id);
    repository.delete(spaceship);
    if (isKafkaEnabled) {
      kafkaProducerService.sendMessage(spaceship, DELETE);
    }
  }
}
