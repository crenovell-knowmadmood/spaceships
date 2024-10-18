package com.w2m.spaceships.services;


import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import java.util.List;
import org.springframework.cache.annotation.Cacheable;

public interface SpaceshipsService {

  // Get all spaceships with pagination
  public List<Spaceship> getAllSpaceShips(int page, int size);

  // Get spaceship by ID
  @Cacheable("spaceShip")
  public Spaceship getSpaceShipById(Long id) throws SpaceShipNotFoundException;

  // Search spaceships by name
  public List<Spaceship> searchSpaceShipsByName(String name);

  // Create a new spaceship
  public Spaceship createSpaceShip(Spaceship spaceShip);

  // Update an existing spaceship
  public Spaceship updateSpaceShip(Long id, Spaceship spaceShip) throws SpaceShipNotFoundException;

  // Delete a spaceship
  public void deleteSpaceShip(Long id) throws SpaceShipNotFoundException;
}
