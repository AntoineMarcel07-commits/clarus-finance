# Clarus Finance

Proyecto escolar de escritorio para controlar finanzas personales. Está hecho con Java Swing, Ant, formularios visuales de NetBeans y JDBC directo. Los datos se guardan en PostgreSQL.

## Funciones

- Inicio de sesión sencillo (`admin` / `1234`).
- Alta, consulta, edición y eliminación de ingresos y gastos.
- Presupuestos por categoría.
- Resumen con ingresos, gastos y saldo.
- Avisos de presupuesto: disponible, cerca del límite o excedido.
- Persistencia en PostgreSQL.

## Requisitos

- JDK 17 o superior.
- Apache NetBeans.
- PostgreSQL 14 o superior ejecutándose en `localhost:5432`.
- Usuario de PostgreSQL llamado `postgres`.

## Instalación rápida

1. En pgAdmin, abre **Query Tool** sobre la base `postgres`.
2. Ejecuta `database/crear_base.sql` una sola vez.
3. Actualiza la lista de bases y abre **Query Tool** sobre `ClarusFinance`.
4. Ejecuta `database/ClarusFinance.sql` para crear tablas y datos de prueba.
5. En `src/clarus/finance/ClarusFinance.java`, cambia `TU_CONTRASENA` por la contraseña de tu usuario `postgres`.
6. Abre la carpeta del proyecto en NetBeans y pulsa **Run Project**.
7. Entra con usuario `admin` y contraseña `1234`.

## Ejecutar desde terminal

```bash
ant clean jar
java -jar dist/ClarusFinance.jar
```

## Estructura sencilla

```text
src/
├── clarus/finance/
│   ├── ClarusFinance.java
│   ├── Movimiento.java
│   ├── Presupuesto.java
│   ├── CalculosFinanzas.java
│   └── OperacionesCalculos.java
└── Interfaces/
    ├── Login.java / Login.form
    ├── MenuPrincipal.java / MenuPrincipal.form
    ├── MovimientosVentana.java / MovimientosVentana.form
    └── PresupuestosVentana.java / PresupuestosVentana.form
```

`Movimiento` y `Presupuesto` tienen las consultas JDBC; `CalculosFinanzas` hace las cuentas; las ventanas muestran y capturan información. No se usan frameworks ni capas complicadas. Los cuatro JFrame se pueden abrir y modificar con la pestaña **Design** de NetBeans.

## Pruebas unitarias

```bash
ant clean test
```

Hay 9 pruebas JUnit 4 en `test/clarus/finance/CalculosFinanzasTest.java`. No abren ventanas ni requieren PostgreSQL.

## SOLID y clean code

- `ClarusFinance` abre una sola conexión, igual que DocuSalud.
- Cada clase tiene una tarea clara.
- `OperacionesCalculos` es una interfaz pequeña.
- Las ventanas declaran los cálculos con `OperacionesCalculos` y usan `CalculosFinanzas` como implementación.
- Las consultas usan `PreparedStatement`.
- Los nombres y métodos están en español y son fáciles de seguir.
- Las consultas usan la misma conexión y reciben los valores con `PreparedStatement`.

## Cumplimiento de la entrega

| Requisito | Evidencia dentro del proyecto |
| --- | --- |
| Principios SOLID y clean code | `OperacionesCalculos`, `CalculosFinanzas`, clases separadas por tarea y explicación en `docs/ARQUITECTURA.md`. |
| Documentación | `README.md`, manual de usuario, arquitectura, pruebas y guía de versionado. |
| Versiones | Historial de Git, `CHANGELOG.md` y etiquetas desde `v0.1.0` hasta `v3.0.0`. |
| Pruebas unitarias | 9 pruebas JUnit en `CalculosFinanzasTest.java`; se ejecutan con `ant clean test`. |
| PDF con el repositorio | `docs/Entrega_Proyecto_Clarus_Finance.pdf`. |

## Documentación

- `docs/Manual_Usuario_Clarus_Finance.pdf`: manual completo e instalación.
- `docs/MANUAL_USUARIO.md`: manual en texto.
- `docs/ARQUITECTURA.md`: explicación del código y SOLID.
- `docs/PRUEBAS.md`: explicación de las pruebas.
- `docs/VERSIONADO.md`: Git y versiones.
- `docs/ENTREGA_PROYECTO.md`: resumen de cumplimiento de la actividad.
- `docs/Entrega_Proyecto_Clarus_Finance.pdf`: PDF listo para subir a la plataforma.
- `CHANGELOG.md`: historial de cambios.

Repositorio: <https://github.com/AntoineMarcel07-commits/clarus-finance>

## Licencia

MIT.
