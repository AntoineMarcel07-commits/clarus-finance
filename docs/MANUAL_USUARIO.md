# Clarus Finance - Manual de usuario e instalación

Versión 1.1.0 - 13 de agosto de 2026

## 1. Descripción

Clarus Finance es una aplicación escolar de escritorio para registrar ingresos y gastos personales. Muestra los movimientos en una tabla y calcula ingresos, gastos y saldo.

## 2. Requisitos

- Windows, macOS o Linux.
- JDK 17 o superior.
- NetBeans con soporte Maven, recomendado.
- Aproximadamente 30 MB libres.

No necesita PostgreSQL ni otro programa de base de datos.

## 3. Instalación en NetBeans

1. Instala JDK 17 y NetBeans.
2. Descarga y descomprime el proyecto.
3. Abre NetBeans.
4. Selecciona **File > Open Project**.
5. Elige la carpeta `ClarusFinance`.
6. Espera a que Maven cargue el proyecto.
7. Abre `ClarusFinanceApp.java` y pulsa **Run File**.

## 4. Instalación desde terminal

Abre una terminal dentro de la carpeta del proyecto y ejecuta:

```bash
mvn clean package
java -jar target/ClarusFinance.jar
```

## 5. Registrar un movimiento

1. Escribe una descripción, por ejemplo `Pago de trabajo`.
2. Escribe una categoría, por ejemplo `Trabajo`.
3. Elige `Ingreso` o `Gasto`.
4. Escribe un monto mayor a cero, por ejemplo `500`.
5. Pulsa **Agregar**.

El nuevo movimiento aparece en la tabla y los totales se actualizan.

## 6. Eliminar un movimiento

1. Selecciona una fila de la tabla.
2. Pulsa **Eliminar seleccionado**.
3. Confirma la eliminación.

## 7. Guardado automático

Los datos se guardan en `datos/movimientos.csv`. La carpeta aparece después de registrar el primer movimiento. Al abrir de nuevo la aplicación, los datos se cargan automáticamente.

Para empezar desde cero, cierra la aplicación y elimina únicamente ese archivo CSV.

## 8. Ejecutar las pruebas

En NetBeans usa **Test Project**. Desde una terminal ejecuta:

```bash
mvn clean test
```

El resultado correcto indica 7 pruebas ejecutadas y 0 fallos.

## 9. Problemas comunes

- **NetBeans no abre el proyecto:** confirma que seleccionaste la carpeta que contiene `pom.xml`.
- **Java no encontrado:** instala JDK 17 y selecciónalo en NetBeans.
- **Monto inválido:** escribe solo números; puedes usar `150.50` o `150,50`.
- **No elimina:** selecciona primero una fila.
- **No guarda:** comprueba que la carpeta del proyecto permita crear archivos.

## 10. Explicación rápida para presentar

- `Movimiento` contiene los datos.
- `VentanaPrincipal` muestra la interfaz.
- `FinanzasServicio` valida y calcula.
- `ArchivoMovimientoRepositorio` guarda el CSV.
- La interfaz `MovimientoRepositorio` permite aplicar SOLID sin complicar el proyecto.
- Las pruebas comprueban la lógica sin abrir la ventana.

Repositorio: <https://github.com/AntoineMarcel07-commits/clarus-finance>
