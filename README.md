# Clarus Finance

Proyecto sencillo de Java Swing hecho con el mismo estilo de los proyectos básicos de NetBeans. Permite agregar ingresos y gastos, verlos en una tabla, eliminarlos y calcular el saldo.

## Funciones

- Agregar un ingreso o gasto.
- Escribir descripción, categoría y monto.
- Mostrar movimientos en una tabla.
- Sumar ingresos y gastos.
- Calcular el saldo.
- Eliminar un movimiento.

Los movimientos se guardan en un `ArrayList` mientras el programa está abierto. Al cerrar la aplicación, la lista empieza de nuevo. Esto mantiene el proyecto fácil de entender y presentar.

## Requisitos

- JDK 17 o superior.
- Apache NetBeans con soporte Maven.

No necesita PostgreSQL ni archivos de configuración.

## Abrir en NetBeans

1. Abre NetBeans.
2. Selecciona **File > Open Project**.
3. Elige la carpeta `ClarusFinance`.
4. Espera a que Maven termine de cargar.
5. Ejecuta `clarus.finance.ClarusFinance`.

## Ejecutar desde terminal

```bash
mvn clean package
java -jar target/ClarusFinance.jar
```

## Código principal

El programa tiene únicamente 5 archivos de producción:

```text
src/main/java/
├── clarus/finance/
│   ├── ClarusFinance.java
│   ├── Movimiento.java
│   ├── Finanzas.java
│   └── OperacionesFinanzas.java
└── Interfaces/
    └── VentanaPrincipal.java
```

- `ClarusFinance`: crea los objetos y abre la ventana.
- `Movimiento`: contiene campos públicos, igual que las clases de tus otros proyectos.
- `Finanzas`: usa un `ArrayList` y realiza las operaciones.
- `OperacionesFinanzas`: interfaz pequeña para demostrar SOLID.
- `VentanaPrincipal`: JFrame con campos, tabla y botones.

## Pruebas unitarias

```bash
mvn clean test
```

Hay 7 pruebas directas en `FinanzasTest.java`. No usan base de datos ni abren ventanas.

## SOLID básico

- **S:** cada clase tiene una tarea.
- **O y L:** otra clase puede implementar `OperacionesFinanzas`.
- **I:** la interfaz contiene solo las operaciones necesarias.
- **D:** la ventana usa la interfaz en vez de depender directamente de `Finanzas`.

No se usan repositorios, capas, patrones empresariales, seguridad avanzada ni archivos CSV.

## Documentación

- `docs/Manual_Usuario_Clarus_Finance.pdf`: manual e instalación.
- `docs/ARQUITECTURA.md`: explicación del código y SOLID.
- `docs/PRUEBAS.md`: explicación de las pruebas.
- `docs/VERSIONADO.md`: Git y versiones.
- `CHANGELOG.md`: historial de cambios.

Repositorio: <https://github.com/AntoineMarcel07-commits/clarus-finance>

## Licencia

MIT.
