package com.w2m.spaceships.services;


import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import java.util.List;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SpaceshipsService {

  // Get all spaceships with pagination
  Page<Spaceship> getAllSpaceShips(Pageable pageable);

  // Get spaceship by ID
  @Cacheable("spaceShip")
  Spaceship getSpaceShipById(Integer id) throws SpaceShipNotFoundException;

  // Search spaceships by name
  List<Spaceship> searchSpaceShipsByName(String name);

  // Create a new spaceship
  Spaceship create(Spaceship spaceShip);

  // Update an existing spaceship
  Spaceship update(Integer id, Spaceship spaceShip) throws SpaceShipNotFoundException;

  // Delete a spaceship
  void delete(Integer id) throws SpaceShipNotFoundException;
}
