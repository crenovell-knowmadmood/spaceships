package com.w2m.spaceships.utils;

import io.confluent.kafka.schemaregistry.ParsedSchema;
import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;
import java.io.IOException;
import org.apache.avro.Schema;
import io.confluent.kafka.schemaregistry.avro.AvroSchema;
public class SchemaRegistryUtils {
  private final SchemaRegistryClient schemaRegistryClient;

  public SchemaRegistryUtils(String schemaRegistryUrl) {
    this.schemaRegistryClient = new CachedSchemaRegistryClient(schemaRegistryUrl, 100);
  }

  public int getSchemaVersion(String subject, Schema schema) throws IOException, RestClientException {
    final ParsedSchema parsedSchema = new AvroSchema(schema);
    return schemaRegistryClient.register(subject, parsedSchema);
  }

  public int getLatestSchemaVersion(String subject) throws IOException, RestClientException {
    return schemaRegistryClient.getLatestSchemaMetadata(subject).getVersion();
  }
  public int registerSchema(String subject, Schema avroSchema) throws Exception {
    AvroSchema schema = new AvroSchema(avroSchema);
    return schemaRegistryClient.register(subject, schema);
  }
}
