# Clarus Finance

Clarus Finance es una aplicación sencilla de escritorio hecha con Java Swing. Sirve para registrar ingresos y gastos, ver los totales y conocer el saldo disponible.

Esta versión fue reducida a propósito para que el código sea fácil de leer, explicar y modificar. No necesita PostgreSQL, usuarios ni configuración externa: guarda los movimientos automáticamente en `datos/movimientos.csv`.

## Funciones

- Registrar un ingreso o un gasto.
- Escribir descripción, categoría y monto.
- Consultar todos los movimientos en una tabla.
- Ver total de ingresos, gastos y saldo.
- Eliminar un movimiento seleccionado.
- Conservar los datos en un archivo local.

## Requisitos

- JDK 17 o superior.
- NetBeans con soporte para Maven, o Maven 3.9.

## Ejecutar en NetBeans

1. Descarga o descomprime el proyecto.
2. Abre NetBeans.
3. Selecciona **File > Open Project**.
4. Elige la carpeta `ClarusFinance`.
5. Espera a que Maven termine de cargar.
6. Ejecuta `ClarusFinanceApp.java`.

No hace falta instalar una base de datos.

## Ejecutar desde terminal

```bash
mvn clean package
java -jar target/ClarusFinance.jar
```

## Pruebas unitarias

```bash
mvn test
```

El proyecto tiene 7 pruebas sencillas para comprobar registros, totales, saldo, validaciones y eliminación.

## Estructura simple

```text
src/main/java/com/clarusfinance/
├── ClarusFinanceApp.java
├── modelo/
│   ├── Movimiento.java
│   └── TipoMovimiento.java
├── datos/
│   ├── MovimientoRepositorio.java
│   └── ArchivoMovimientoRepositorio.java
├── servicio/
│   └── FinanzasServicio.java
└── vista/
    └── VentanaPrincipal.java
```

Solo existen 7 clases de producción:

- `Movimiento`: contiene los datos de un movimiento.
- `TipoMovimiento`: define ingreso o gasto.
- `MovimientoRepositorio`: indica las operaciones para guardar datos.
- `ArchivoMovimientoRepositorio`: guarda los datos en un CSV.
- `FinanzasServicio`: valida y calcula los totales.
- `VentanaPrincipal`: contiene la interfaz Swing.
- `ClarusFinanceApp`: inicia el programa.

## SOLID explicado de forma sencilla

- **S:** cada clase tiene una tarea principal.
- **O:** se puede crear otra forma de guardar datos sin cambiar el servicio.
- **L:** cualquier clase que cumpla `MovimientoRepositorio` puede reemplazar al archivo.
- **I:** la interfaz solo pide tres operaciones pequeñas.
- **D:** `FinanzasServicio` depende de la interfaz y no del archivo CSV.

La explicación completa está en `docs/ARQUITECTURA.md`.

## Documentación

- Manual e instalación: `docs/Manual_Usuario_Clarus_Finance.pdf`
- Arquitectura y SOLID: `docs/ARQUITECTURA.md`
- Pruebas: `docs/PRUEBAS.md`
- Versionado: `docs/VERSIONADO.md`
- Cambios: `CHANGELOG.md`

Repositorio: <https://github.com/AntoineMarcel07-commits/clarus-finance>

## Licencia

MIT.
