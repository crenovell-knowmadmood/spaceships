package com.w2m.spaceships.services.impl;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import com.w2m.spaceships.repositories.SpaceshipRepository;
import com.w2m.spaceships.services.SpaceshipsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class SpaceshipsServiceImpl implements SpaceshipsService {
  @Autowired
  SpaceshipRepository repository;


  @Override
  public List<Spaceship> getAllSpaceShips(int page, int size) {
    return repository.findAll(PageRequest.of(page, size)).getContent();
  }

  @Override
  public Spaceship getSpaceShipById(Long id) throws SpaceShipNotFoundException {
    return repository.findById(id)
        .orElseThrow(() -> new SpaceShipNotFoundException(String.format("SpaceShip with ID %d not found.",id)));
  }

  @Override
  public List<Spaceship> searchSpaceShipsByName(String name) {
    return repository.findByNameContaining(name);
  }

  @Override
  public Spaceship createSpaceShip(Spaceship spaceShip) {
    return repository.save(spaceShip);
  }

  @Override
  public Spaceship updateSpaceShip(Long id, Spaceship spaceShip) throws SpaceShipNotFoundException {
    Spaceship existingSpaceShip = getSpaceShipById(id);
    existingSpaceShip.setName(spaceShip.getName());
    existingSpaceShip.setType(spaceShip.getType());
    existingSpaceShip.setOrigin(spaceShip.getOrigin());
    return repository.save(existingSpaceShip);
  }

  @Override
  public void deleteSpaceShip(Long id) throws SpaceShipNotFoundException {
    Spaceship existingSpaceShip = getSpaceShipById(id);
    repository.delete(existingSpaceShip);
  }
}
