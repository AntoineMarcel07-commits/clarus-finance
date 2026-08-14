# Pruebas unitarias

Las pruebas están en `FinanzasServicioTest.java` y no abren ventanas ni crean archivos. Usan una lista en memoria para probar únicamente la lógica.

## Ejecutar

```bash
mvn clean test
```

## Casos comprobados

1. Registra un ingreso.
2. Registra un gasto.
3. Calcula el saldo.
4. Asigna ids consecutivos.
5. Rechaza una descripción vacía.
6. Rechaza un monto negativo.
7. Elimina un movimiento.

## Cómo leer una prueba

Cada prueba tiene tres pasos:

1. Preparar los datos.
2. Ejecutar un método del servicio.
3. Comparar el resultado esperado con `assertEquals` o `assertThrows`.

Esto mantiene las pruebas pequeñas y fáciles de explicar.
