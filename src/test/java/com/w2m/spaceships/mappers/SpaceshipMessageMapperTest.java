package com.w2m.spaceships.mappers;

import static com.w2m.spaceships.constants.EventConstants.CREATE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.kafka.messages.SpaceshipMessageKey;
import com.w2m.spaceships.kafka.messages.SpaceshipMessagePayload;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

class SpaceshipMessageMapperTest {

  SpaceshipMessageMapper mapper = Mappers.getMapper(SpaceshipMessageMapper.class);

  @Test
  void toMessagePayload() {
    Spaceship entity = new  Spaceship();
    entity.setId(1);
    entity.setName("name");
    entity.setType("type");

    SpaceshipMessagePayload payload = mapper.toMessagePayload(entity, CREATE);

    assertEquals(CREATE, payload.getAction());
    assertEquals(entity.getId(), payload.getId());
    assertEquals(entity.getName(), payload.getName());
  }

  @Test
  void toMessageKey() {
    Spaceship entity = new  Spaceship();
    entity.setId(1);
    entity.setName("name");
    entity.setType("type");
    SpaceshipMessageKey key = mapper.toMessageKey(entity);

    assertEquals(entity.getId(),key.getId());

  }
}