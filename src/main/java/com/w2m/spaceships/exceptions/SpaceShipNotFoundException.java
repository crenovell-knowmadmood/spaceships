package com.w2m.spaceships.exceptions;

public class SpaceShipNotFoundException extends Exception{
  public SpaceShipNotFoundException(String msg){
    super(msg);
  }

  public SpaceShipNotFoundException(String message, Throwable cause){
    super(message);
  }


}
