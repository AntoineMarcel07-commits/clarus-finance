# Clarus Finance - Manual de usuario e instalación

Versión 1.2.0 - 14 de agosto de 2026

## 1. Descripción

Clarus Finance es una aplicación escolar de Java Swing. Permite agregar ingresos y gastos, mostrarlos en una tabla, eliminarlos y calcular el saldo.

## 2. Requisitos

- JDK 17 o superior.
- Apache NetBeans con Maven.
- Windows, macOS o Linux.

No necesita PostgreSQL.

## 3. Instalación

1. Descomprime el proyecto.
2. Abre Apache NetBeans.
3. Selecciona **File > Open Project**.
4. Elige la carpeta `ClarusFinance`.
5. Espera a que Maven termine de cargar.
6. Ejecuta la clase `clarus.finance.ClarusFinance`.

## 4. Registrar un movimiento

1. Escribe una descripción.
2. Escribe una categoría.
3. Selecciona `Ingreso` o `Gasto`.
4. Escribe un monto mayor a cero.
5. Pulsa **AGREGAR**.

## 5. Eliminar

Selecciona una fila y pulsa **ELIMINAR**.

## 6. Totales

- Ingresos: suma los movimientos de tipo `Ingreso`.
- Gastos: suma los movimientos de tipo `Gasto`.
- Saldo: ingresos menos gastos.

## 7. Datos temporales

Los movimientos viven en un `ArrayList` mientras el programa está abierto. Al cerrar la aplicación, la lista se borra. Esto evita instalaciones y código de almacenamiento avanzado.

## 8. Pruebas

```bash
mvn clean test
```

El resultado correcto muestra 7 pruebas y 0 fallos.

## 9. Explicación rápida

- `Movimiento` guarda los datos.
- `Finanzas` contiene el ArrayList y hace las cuentas.
- `VentanaPrincipal` es el JFrame.
- `ClarusFinance` abre el programa.
- `OperacionesFinanzas` es la interfaz sencilla usada para demostrar SOLID.

## 10. Problemas comunes

- Si no abre, revisa que NetBeans use JDK 17.
- Si no agrega, completa todos los campos.
- Si el monto falla, escribe únicamente números.
- Si no elimina, selecciona primero una fila.

Repositorio: <https://github.com/AntoineMarcel07-commits/clarus-finance>
