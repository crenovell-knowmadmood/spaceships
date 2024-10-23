package com.w2m.spaceships.controllers;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import com.w2m.spaceships.services.SpaceshipsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpaceshipsControllerImpl implements SpaceshipsController {

  @Autowired
  private SpaceshipsService service;

  @Override
  public ResponseEntity<Page<Spaceship>> getAll(Pageable pageable) {
    return ResponseEntity.ok(service.getAllSpaceShips(pageable));
  }

  @Override
  public ResponseEntity<Spaceship> getById(@PathVariable Integer id) throws SpaceShipNotFoundException {
    final Spaceship spaceship = service.getSpaceShipById(id);
    return ResponseEntity.ok(spaceship);
  }

  @Override
  public ResponseEntity<Spaceship> create(@RequestBody Spaceship spaceship) {
    final Spaceship spaceShip = service.create(spaceship);
    return ResponseEntity.ok(spaceShip);
  }

  @Override
  public ResponseEntity<Spaceship> updateById(Integer id, Spaceship spaceship) throws SpaceShipNotFoundException {
    Spaceship spaceShip = service.update(id, spaceship);
    return ResponseEntity.ok(spaceShip);
  }

  @Override
  public void delete(Integer id) throws SpaceShipNotFoundException {
    service.delete(id);
  }
}
