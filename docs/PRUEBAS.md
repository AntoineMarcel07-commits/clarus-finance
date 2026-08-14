# Pruebas unitarias

Las pruebas están en `test/clarus/finance/CalculosFinanzasTest.java`.

## Ejecutar

```bash
ant clean test
```

## Pruebas

1. Sumar ingresos.
2. Sumar gastos.
3. Calcular el saldo.
4. Sumar gastos por categoría.
5. Detectar presupuesto disponible.
6. Detectar presupuesto cerca del límite.
7. Detectar presupuesto excedido.
8. Crear un movimiento.
9. Crear un presupuesto.

Cada prueba usa objetos sencillos, llama a un método y compara el resultado con `assertEquals`.

PostgreSQL y Swing no se usan en las pruebas unitarias. Esto permite comprobar rápidamente las reglas principales sin depender de una instalación local.

Resultado esperado:

```text
Tests run: 9, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```
