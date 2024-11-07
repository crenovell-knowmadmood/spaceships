# Creación del Schema Spaceships


Crear el fichero **create-schema.snippet** con el siguiente código
```
import com.w2m.spaceships.entities.Spaceship;
import com.w2m.spaceships.utils.AvroSchemaGenerator;
import com.w2m.spaceships.utils.SchemaRegistryUtils;
import org.apache.avro.Schema;


final Schema schema1 = AvroSchemaGenerator.generateSchema(Spaceship.class);
final String schemaRegistryUrl = "http://localhost:8084";
final String subject1 = "spaceships-out";

SchemaRegistryUtils schemaRegistryUtils = new SchemaRegistryUtils(schemaRegistryUrl);
final int version;
try {
  version = schemaRegistryUtils.registerSchema(subject1, schema1);
  System.out.println("Se ha registrado la version: " + version);
} catch (Exception e) {
  throw new RuntimeException(e);
}
```


# Construcción y levantamiento
```
docker-compose up
```