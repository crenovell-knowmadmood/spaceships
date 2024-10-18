package com.w2m.spaceships.repositories;

import com.w2m.spaceships.entities.Spaceship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpaceshipRepository extends JpaRepository<Spaceship,Long> {
  List<Spaceship> findByNameContaining(String nombre);
}
