# Base de datos

La aplicación crea automáticamente las tablas y los datos de demostración cuando logra conectarse.

1. Crea una base PostgreSQL llamada `clarus_finance` con `create_database.sql` o pgAdmin.
2. Copia `config/application.properties.example` como `config/application.properties`.
3. Cambia usuario y contraseña para que coincidan con tu instalación.

El esquema versionado está en `src/main/resources/db/schema.sql` y los datos iniciales en `seed.sql`.
