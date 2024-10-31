package com.w2m.spaceships.services.impl;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.kafka.messages.SpaceshipMessageKey;
import com.w2m.spaceships.kafka.messages.SpaceshipMessagePayload;
import com.w2m.spaceships.mappers.SpaceshipMessageMapper;
import com.w2m.spaceships.services.KafkaProducerService;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerServiceImpl implements KafkaProducerService {

  @Autowired
  SpaceshipMessageMapper mapper;

  @Override
  public Supplier<Message<SpaceshipMessagePayload>> sendMessage(Spaceship spaceship, String action) {
    return () -> {
      final SpaceshipMessageKey key = mapper.toMessageKey(spaceship);
      final SpaceshipMessagePayload payload = mapper.toMessagePayload(spaceship, action);

      return MessageBuilder.withPayload(payload).setHeader("key", key).build();
    };
  }
}
