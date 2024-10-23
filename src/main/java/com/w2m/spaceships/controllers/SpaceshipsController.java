package com.w2m.spaceships.controllers;

import static com.w2m.spaceships.constants.MappingConstants.GET_ALL_URL;
import static com.w2m.spaceships.constants.MappingConstants.GET_BY_ID_URL;
import static com.w2m.spaceships.constants.MappingConstants.CREATE_URL;
import static com.w2m.spaceships.constants.MappingConstants.UPDATE_URL;
import static com.w2m.spaceships.constants.MappingConstants.DELETE_URL;

import com.w2m.spaceships.constants.MappingConstants;
import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.exceptions.SpaceShipNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public interface SpaceshipsController {


  @GetMapping(GET_ALL_URL)
  ResponseEntity<Page<Spaceship>> getAll(Pageable pageable);
  @GetMapping(GET_BY_ID_URL)
  ResponseEntity<Spaceship> getById(@PathVariable Integer id) throws SpaceShipNotFoundException;
  @PostMapping(MappingConstants.CREATE_URL)
  ResponseEntity<Spaceship> create(@RequestBody Spaceship spaceship);
  @PutMapping(MappingConstants.UPDATE_URL)
  ResponseEntity<Spaceship> updateById(@PathVariable Integer id, @RequestBody Spaceship spaceship)
      throws SpaceShipNotFoundException;
  @DeleteMapping(MappingConstants.DELETE_URL)
  void delete(@PathVariable Integer id) throws SpaceShipNotFoundException;
}
