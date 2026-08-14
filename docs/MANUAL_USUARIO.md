# Clarus Finance - Manual de usuario e instalación

Versión 2.0.0 - 14 de agosto de 2026

## 1. Descripción

Clarus Finance es una aplicación escolar de Java Swing para administrar finanzas personales. Guarda ingresos, gastos y presupuestos en PostgreSQL. Además, presenta totales y alertas en un dashboard.

## 2. Requisitos

- JDK 17 o superior.
- Apache NetBeans con Maven, o Maven 3.9+.
- PostgreSQL 14 o superior.
- Usuario local de PostgreSQL llamado `postgres`.
- Windows, macOS o Linux.

## 3. Preparar PostgreSQL

1. Inicia el servicio de PostgreSQL.
2. Abre pgAdmin e ingresa con tu contraseña.
3. Selecciona la base `postgres` y abre **Query Tool**.
4. Ejecuta el archivo `database/crear_base.sql`.
5. Comprueba que aparezca la base `clarus_finance`.

No debes crear las tablas manualmente: la aplicación crea `movimientos` y `presupuestos` en el primer inicio.

## 4. Abrir y ejecutar

1. Descomprime el proyecto.
2. En NetBeans selecciona **File > Open Project**.
3. Abre la carpeta `ClarusFinance` y espera a que Maven cargue.
4. Ejecuta la clase `clarus.finance.ClarusFinance`.
5. Escribe tu contraseña local de PostgreSQL en la primera ventana.
6. Inicia sesión con usuario `admin` y contraseña `1234`.

También puedes ejecutar:

```bash
mvn clean package
java -jar target/ClarusFinance.jar
```

## 5. Menú principal

- **Dashboard:** muestra ingresos, gastos, saldo, últimos movimientos y presupuestos.
- **Movimientos:** administra ingresos y gastos.
- **Presupuestos:** administra límites por categoría.
- **Cerrar sesión:** vuelve al login.

## 6. Movimientos

Para agregar, completa descripción, categoría, tipo, monto y fecha `AAAA-MM-DD`; luego pulsa **AGREGAR**.

Para editar, selecciona una fila, cambia los datos y pulsa **ACTUALIZAR**. Para borrar, selecciona una fila y pulsa **ELIMINAR**; el programa pide confirmación.

## 7. Presupuestos

Escribe una categoría y un límite mayor a cero. **GUARDAR / ACTUALIZAR** crea el presupuesto o actualiza el límite si esa categoría ya existe. Una fila seleccionada también se puede eliminar.

El dashboard compara los gastos de cada categoría con su límite:

- `Disponible`: menos de 80 % usado.
- `Cerca del límite`: de 80 % a 100 % usado.
- `Excedido`: gasto mayor al límite.

## 8. Datos y seguridad

Los datos permanecen en la base `clarus_finance` aunque cierres el programa. La contraseña de PostgreSQL solo se usa para abrir la conexión durante esa ejecución; no se guarda en el código ni en Git.

El login `admin` / `1234` es demostrativo para una tarea escolar, no un sistema de seguridad para producción.

## 9. Pruebas

```bash
mvn clean test
```

El resultado correcto muestra 9 pruebas y 0 fallos. Estas pruebas revisan cálculos, estados de presupuesto y creación de objetos sin necesitar una base de datos.

## 10. Problemas comunes

- **No conecta:** confirma que PostgreSQL esté activo en `localhost:5432`, que exista `clarus_finance` y que la contraseña sea correcta.
- **La base ya existe:** no vuelvas a ejecutar `CREATE DATABASE`; continúa con la ejecución de la app.
- **No guarda:** completa todos los campos y usa un monto mayor a cero.
- **Fecha inválida:** usa el formato `AAAA-MM-DD`, por ejemplo `2026-08-14`.
- **No actualiza o elimina:** primero selecciona una fila de la tabla.
- **No abre el JAR:** verifica que Java 17 esté instalado.

Repositorio: <https://github.com/AntoineMarcel07-commits/clarus-finance>
