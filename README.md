# Clarus Finance

Aplicación escolar de escritorio para controlar finanzas personales. Está hecha con Java Swing, Maven y JDBC directo, siguiendo el estilo sencillo de un proyecto de NetBeans. Los datos se guardan en PostgreSQL.

## Funciones

- Inicio de sesión sencillo (`admin` / `1234`).
- Alta, consulta, edición y eliminación de ingresos y gastos.
- Presupuestos por categoría.
- Dashboard con ingresos, gastos, saldo y últimos movimientos.
- Avisos de presupuesto: disponible, cerca del límite o excedido.
- Persistencia en PostgreSQL.

## Requisitos

- JDK 17 o superior.
- Apache NetBeans con Maven, o Maven 3.9+.
- PostgreSQL 14 o superior ejecutándose en `localhost:5432`.
- Usuario de PostgreSQL llamado `postgres`.

## Instalación rápida

1. En pgAdmin, abre **Query Tool** sobre la base `postgres`.
2. Ejecuta `database/crear_base.sql` una sola vez.
3. Abre la carpeta `ClarusFinance` en NetBeans.
4. Ejecuta `clarus.finance.ClarusFinance`.
5. Escribe la contraseña local del usuario `postgres` cuando la aplicación la solicite.
6. Entra con usuario `admin` y contraseña `1234`.

La contraseña de PostgreSQL no está escrita ni guardada dentro del proyecto. Las tablas `movimientos` y `presupuestos` se crean automáticamente en el primer inicio.

## Ejecutar desde terminal

```bash
mvn clean package
java -jar target/ClarusFinance.jar
```

## Estructura sencilla

```text
src/main/java/
├── clarus/finance/
│   ├── ClarusFinance.java
│   ├── ConexionBD.java
│   ├── Movimiento.java
│   ├── Presupuesto.java
│   ├── CalculosFinanzas.java
│   └── OperacionesCalculos.java
└── Interfaces/
    ├── Login.java
    ├── MenuPrincipal.java
    ├── Dashboard.java
    ├── MovimientosVentana.java
    └── PresupuestosVentana.java
```

`Movimiento` y `Presupuesto` contienen consultas JDBC directas; `CalculosFinanzas` hace las cuentas; las ventanas muestran y capturan información. No se usan frameworks, repositorios genéricos ni una arquitectura complicada.

## Pruebas unitarias

```bash
mvn clean test
```

Hay 9 pruebas directas en `CalculosFinanzasTest.java`. No abren ventanas ni requieren PostgreSQL.

## SOLID y clean code

- Cada clase tiene una responsabilidad clara.
- `OperacionesCalculos` es una interfaz pequeña.
- `ClarusFinance` declara los cálculos mediante esa interfaz y usa `CalculosFinanzas` como implementación.
- Las consultas usan `PreparedStatement`.
- Los nombres y métodos están en español y son fáciles de seguir.
- La contraseña de PostgreSQL se pide al iniciar y no se versiona.

## Cumplimiento de la entrega

| Requisito | Evidencia dentro del proyecto |
| --- | --- |
| Principios SOLID y clean code | `OperacionesCalculos`, `CalculosFinanzas`, separación entre conexión, cálculos y ventanas, y explicación en `docs/ARQUITECTURA.md`. |
| Documentación | `README.md`, manual de usuario, arquitectura, pruebas y guía de versionado. |
| Versiones | Historial de Git, `CHANGELOG.md` y etiquetas desde `v0.1.0` hasta `v2.1.0`. |
| Pruebas unitarias | 9 pruebas JUnit en `CalculosFinanzasTest.java`; se ejecutan con `mvn clean test`. |
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
