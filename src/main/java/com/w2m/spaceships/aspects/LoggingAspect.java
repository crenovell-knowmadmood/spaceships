package com.w2m.spaceships.aspects;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

  private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

  // El Pointcut intercepta cualquier método llamado 'getSpaceshipById' de cualquier clase
  @Before("execution(* com.w2m.wings.services.impl.SpaceshipsServiceImpl.getSpaceShipById && args(id,..)")
  public void logIfNegativeId(int id) {
    if (id < 0) {
      logger.warn("Attempted to get spaceship with negative ID: " + id);
    }
  }
}