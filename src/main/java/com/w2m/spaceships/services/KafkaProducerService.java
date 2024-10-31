package com.w2m.spaceships.services;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.kafka.messages.SpaceshipMessagePayload;
import java.util.function.Supplier;
import org.springframework.messaging.Message;

public interface KafkaProducerService {


  /**
   * @param spaceship
   * @param action
   * @return
   */
  Supplier<Message<SpaceshipMessagePayload>> sendMessage(Spaceship spaceship, String action);
}
