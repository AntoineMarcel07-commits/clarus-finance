# Arquitectura sencilla

## Flujo del programa

```text
ClarusFinance -> ConexionBD -> PostgreSQL
       |
       v
     Login -> MenuPrincipal
                  |
        +---------+----------+
        |                    |
        v                    v
   Dashboard        Movimientos / Presupuestos
        |                    |
        +----> clases con JDBC directo
```

## Clases

- `ClarusFinance`: clase `main`; pide la contraseña de PostgreSQL, conecta y abre el login.
- `ConexionBD`: abre la conexión y crea las tablas si todavía no existen.
- `Movimiento`: datos de un ingreso o gasto y métodos JDBC para insertar, consultar, actualizar y eliminar.
- `Presupuesto`: datos de un límite por categoría y métodos JDBC para guardar, consultar y eliminar.
- `CalculosFinanzas`: suma ingresos, gastos, saldo y determina el estado de un presupuesto.
- `OperacionesCalculos`: interfaz pequeña con las operaciones matemáticas.
- `Login`, `MenuPrincipal`, `Dashboard`, `MovimientosVentana` y `PresupuestosVentana`: JFrames de la aplicación.

`InfoMovimiento` e `InfoPresupuesto` conservan el patrón sencillo de los proyectos de clase: reciben datos, crean un objeto y lo devuelven.

## Base de datos

```text
movimientos
- id, tipo, descripcion, categoria, monto, fecha

presupuestos
- id, categoria, limite
```

La categoría de un presupuesto es única. Si se vuelve a guardar la misma categoría, PostgreSQL actualiza su límite. Todas las consultas que reciben valores del usuario usan `PreparedStatement`.

## SOLID explicado fácil

- **Responsabilidad única:** la conexión, los datos, los cálculos y las ventanas están separados.
- **Abierto/cerrado:** se puede agregar otra implementación de cálculos sin modificar la interfaz.
- **Sustitución:** cualquier implementación correcta de `OperacionesCalculos` puede reemplazar a `CalculosFinanzas`.
- **Segregación:** la interfaz solo contiene cinco operaciones necesarias para el dashboard.
- **Inversión de dependencias:** el objeto global de cálculos está declarado como `OperacionesCalculos`, no como una implementación fija.

## Clean code

- Nombres directos en español.
- Métodos pequeños para guardar, listar, calcular y validar.
- Validación de textos, monto positivo y fecha.
- Contraseña de PostgreSQL solicitada en cada inicio; no está en Git.
- Sin frameworks, capas de servicios ni patrones difíciles de explicar.

Es una aplicación intermedia: ofrece persistencia y varias pantallas, pero sigue siendo entendible para una exposición escolar.
