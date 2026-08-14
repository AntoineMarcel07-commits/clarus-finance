# Pruebas unitarias

Las pruebas están en `src/test/java/clarus/finance/FinanzasTest.java`.

## Ejecutar

```bash
mvn clean test
```

## Pruebas

1. Agregar un ingreso.
2. Agregar un gasto.
3. Sumar ingresos.
4. Sumar gastos.
5. Calcular el saldo.
6. Rechazar un monto negativo.
7. Eliminar un movimiento.

Cada prueba crea un objeto `Finanzas`, llama a un método y compara el resultado con `assertEquals` o `assertNull`.

No se usan objetos falsos, repositorios, archivos temporales ni PostgreSQL.
