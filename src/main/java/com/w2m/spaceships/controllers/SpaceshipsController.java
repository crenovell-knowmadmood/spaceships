package com.w2m.spaceships.controllers;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface SpaceshipsController {
  @GetMapping("/spaceships/get-all")
  ResponseEntity<Page<Spaceship>> getAll(Pageable pageable);
  @GetMapping("/spaceships/id/{id}")
  ResponseEntity<Spaceship> getById(@PathVariable Integer id) throws SpaceShipNotFoundException;
  @PostMapping("/spaceships/create")
  ResponseEntity<Spaceship> create(@RequestBody Spaceship spaceship);
  @PutMapping("/spaceships/update")
  ResponseEntity<Spaceship> updateById(@PathVariable Integer id, @RequestBody Spaceship spaceship)
      throws SpaceShipNotFoundException;
  void delete(@PathVariable Integer id) throws SpaceShipNotFoundException;


}
