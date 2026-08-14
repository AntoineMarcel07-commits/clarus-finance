# Entrega del proyecto personal - Clarus Finance

Versión 2.1.0 - 14 de agosto de 2026

Repositorio público:

<https://github.com/AntoineMarcel07-commits/clarus-finance>

## Descripción

Clarus Finance es una aplicación de escritorio para registrar ingresos, gastos y presupuestos personales. Fue desarrollada con Java Swing, Maven y PostgreSQL. La intención es mantener una estructura sencilla, parecida a un proyecto de clase hecho en NetBeans.

## 1. Principios SOLID y clean code

- Las ventanas se encargan de mostrar y capturar información.
- `ConexionBD` se encarga de abrir PostgreSQL y preparar las tablas.
- `CalculosFinanzas` contiene las reglas para ingresos, gastos, saldo y presupuestos.
- `OperacionesCalculos` es una interfaz pequeña que evita depender directamente de una sola implementación.
- Los métodos y variables tienen nombres descriptivos en español.
- Las consultas que reciben datos usan `PreparedStatement`.
- La contraseña de PostgreSQL no se guarda en el repositorio.

La explicación completa está en `docs/ARQUITECTURA.md`.

## 2. Documentación

El proyecto incluye:

- `README.md`: descripción e instalación rápida.
- `docs/MANUAL_USUARIO.md`: guía de instalación y uso.
- `docs/Manual_Usuario_Clarus_Finance.pdf`: manual para el usuario.
- `docs/ARQUITECTURA.md`: estructura y principios SOLID.
- `docs/PRUEBAS.md`: pruebas unitarias y resultado esperado.
- `docs/VERSIONADO.md`: uso de Git y etiquetas.
- `CHANGELOG.md`: cambios realizados por versión.

## 3. Versiones

El desarrollo se guardó con Git. Las etiquetas principales son:

- `v0.1.0`: estructura inicial.
- `v0.2.0`: interfaz Swing.
- `v0.3.0`: pruebas unitarias.
- `v1.0.0`: primera entrega documentada.
- `v1.1.0` y `v1.2.0`: simplificación del proyecto.
- `v2.0.0`: PostgreSQL, CRUD, dashboard y presupuestos.
- `v2.1.0`: documento final de cumplimiento.

## 4. Pruebas unitarias

Las 9 pruebas están en `src/test/java/clarus/finance/CalculosFinanzasTest.java` y revisan:

1. Total de ingresos.
2. Total de gastos.
3. Saldo disponible.
4. Gastos por categoría.
5. Presupuesto disponible.
6. Presupuesto cerca del límite.
7. Presupuesto excedido.
8. Creación de un movimiento.
9. Creación de un presupuesto.

Comando para ejecutarlas:

```bash
mvn clean test
```

Resultado verificado: 9 pruebas ejecutadas, 0 fallos y 0 errores.

## 5. Entrega

El archivo `docs/Entrega_Proyecto_Clarus_Finance.pdf` reúne estos puntos y contiene el enlace público del repositorio para subirlo directamente a la plataforma escolar.
