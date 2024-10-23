package com.w2m.spaceships.repositories;

import com.w2m.spaceships.entities.Spaceship;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpaceshipRepository extends JpaRepository<Spaceship, Integer> {
  List<Spaceship> findByNameContaining(String nombre);
}
