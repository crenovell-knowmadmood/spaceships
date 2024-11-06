package com.w2m.spaceships.utils;

import org.apache.avro.Schema;
import org.apache.avro.reflect.ReflectData;

public class AvroSchemaGenerator {
  public static Schema generateSchema(Class<?> cl){
    return ReflectData.get().getSchema(cl);
  }
}
