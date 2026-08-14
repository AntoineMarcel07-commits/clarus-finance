# Clarus Finance - Manual de usuario e instalación

Versión 3.0.0

## 1. Descripción

Clarus Finance es una aplicación escolar de Java Swing para administrar finanzas personales. Guarda ingresos, gastos y presupuestos en PostgreSQL. Además, presenta totales y alertas de presupuesto.

## 2. Requisitos

- JDK 17 o superior.
- Apache NetBeans.
- PostgreSQL 14 o superior.
- Usuario local de PostgreSQL llamado `postgres`.
- Windows, macOS o Linux.

## 3. Preparar PostgreSQL

1. Inicia el servicio de PostgreSQL.
2. Abre pgAdmin e ingresa con tu contraseña.
3. Selecciona la base `postgres` y abre **Query Tool**.
4. Ejecuta el archivo `database/crear_base.sql`.
5. Actualiza la lista de bases y selecciona `ClarusFinance`.
6. Abre otro **Query Tool** y ejecuta `database/ClarusFinance.sql`.
7. Comprueba que aparezcan las tablas `movimientos` y `presupuestos`.

## 4. Abrir y ejecutar

1. Descomprime el proyecto.
2. En NetBeans selecciona **File > Open Project**.
3. Abre la carpeta `ClarusFinance`.
4. Abre `src/clarus/finance/ClarusFinance.java`.
5. Cambia `TU_CONTRASENA` por la contraseña de PostgreSQL.
6. Pulsa **Run Project**.
7. Inicia sesión con usuario `admin` y contraseña `1234`.

También puedes ejecutar:

```bash
ant clean jar
java -jar dist/ClarusFinance.jar
```

## 5. Menú principal

- **Resumen:** muestra ingresos, gastos y saldo.
- **Movimientos:** administra ingresos y gastos.
- **Presupuestos:** administra límites por categoría.
- **Cerrar sesión:** vuelve al login.

## 6. Movimientos

Para agregar, completa descripción, categoría, tipo, monto y fecha `AAAA-MM-DD`; luego pulsa **GUARDAR**.

Para editar, selecciona una fila, cambia los datos y pulsa **ACTUALIZAR**. Para borrar, selecciona una fila y pulsa **ELIMINAR**; el programa pide confirmación.

## 7. Presupuestos

Escribe una categoría y un límite mayor a cero. **GUARDAR / ACTUALIZAR** crea el presupuesto o actualiza el límite si esa categoría ya existe. Una fila seleccionada también se puede eliminar.

La tabla de presupuestos compara los gastos de cada categoría con su límite:

- `Disponible`: menos de 80 % usado.
- `Cerca del límite`: de 80 % a 100 % usado.
- `Excedido`: gasto mayor al límite.

## 8. Datos y seguridad

Los datos permanecen en la base `ClarusFinance` aunque cierres el programa. La aplicación usa una sola conexión para todas las consultas.

El login `admin` / `1234` es demostrativo para una tarea escolar, no un sistema de seguridad para producción.

## 9. Pruebas

```bash
ant clean test
```

El resultado correcto muestra 9 pruebas y 0 fallos. Estas pruebas revisan cálculos, estados de presupuesto y creación de objetos sin necesitar una base de datos.

## 10. Problemas comunes

- **No conecta:** confirma que PostgreSQL esté activo en `localhost:5432`, que exista `ClarusFinance` y que cambiaste `TU_CONTRASENA`.
- **La base ya existe:** no vuelvas a ejecutar `CREATE DATABASE`; continúa con la ejecución de la app.
- **No guarda:** completa todos los campos y usa un monto mayor a cero.
- **Fecha inválida:** usa exactamente el formato `AAAA-MM-DD`.
- **No actualiza o elimina:** primero selecciona una fila de la tabla.
- **No abre el JAR:** verifica que Java 17 esté instalado.

Repositorio: <https://github.com/AntoineMarcel07-commits/clarus-finance>
