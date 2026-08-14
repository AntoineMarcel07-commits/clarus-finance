# Arquitectura sencilla

## Flujo del programa

```text
ClarusFinance -> PostgreSQL
       |
       v
     Login -> MenuPrincipal
                  |
        +---------+----------+
        |                    |
        v                    v
  Movimientos          Presupuestos
        |                    |
        +----> clases con JDBC directo
```

## Clases

- `ClarusFinance`: clase `main`; abre la única conexión a PostgreSQL y muestra el login.
- `Movimiento`: datos de un ingreso o gasto y métodos JDBC para insertar, consultar, actualizar y eliminar.
- `Presupuesto`: datos de un límite por categoría y métodos JDBC para guardar, consultar y eliminar.
- `CalculosFinanzas`: suma ingresos, gastos, saldo y determina el estado de un presupuesto.
- `OperacionesCalculos`: interfaz pequeña con las operaciones matemáticas.
- `Login`, `MenuPrincipal`, `MovimientosVentana` y `PresupuestosVentana`: JFrame Forms editables desde NetBeans.

`InfoMovimiento` e `InfoPresupuesto` conservan el patrón sencillo de los proyectos de clase: reciben datos, crean un objeto y lo devuelven.

## Base de datos

```text
movimientos
- id_movimiento, fecha, tipo, categoria, descripcion, monto

presupuestos
- id_presupuesto, categoria, limite
```

La categoría de un presupuesto es única. Si se vuelve a guardar la misma categoría, PostgreSQL actualiza su límite. Todas las consultas que reciben valores del usuario usan `PreparedStatement`.

## SOLID explicado fácil

- **Responsabilidad única:** la conexión, los datos, los cálculos y las ventanas tienen una tarea clara.
- **Abierto/cerrado:** se puede agregar otra implementación de cálculos sin modificar la interfaz.
- **Sustitución:** cualquier implementación correcta de `OperacionesCalculos` puede reemplazar a `CalculosFinanzas`.
- **Segregación:** la interfaz solo contiene cinco operaciones necesarias para el dashboard.
- **Inversión de dependencias:** las operaciones principales están definidas en `OperacionesCalculos`.

## Clean code

- Nombres directos en español.
- Métodos pequeños para guardar, listar, calcular y validar.
- Validación de textos, monto positivo y fecha.
- Una sola conexión compartida, como en DocuSalud.
- Sin frameworks, capas de servicios ni patrones difíciles de explicar.

Es una aplicación intermedia: ofrece persistencia y varias pantallas, pero sigue siendo entendible para una exposición escolar.
