package com.w2m.spaceships.mappers;

import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.kafka.messages.SpaceshipMessageKey;
import com.w2m.spaceships.kafka.messages.SpaceshipMessagePayload;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring")
public interface SpaceshipMessageMapper {

  @Mapping(source = "entity.id", target = "id")
  @Mapping(source = "entity.type", target = "type")
  @Mapping(source = "entity.name", target = "name")
  SpaceshipMessagePayload  toMessagePayload(Spaceship entity, String action);

  @Mapping(source = "id", target="id")
  SpaceshipMessageKey toMessageKey(Spaceship entity);

}
