# Arquitectura sencilla y principios SOLID

## Idea general

La aplicación se divide en partes pequeñas para que cada archivo tenga una tarea fácil de explicar:

```text
VentanaPrincipal
       |
       v
FinanzasServicio
       |
       v
MovimientoRepositorio <--- ArchivoMovimientoRepositorio
       |
       v
Movimiento
```

## Responsabilidad de cada parte

### Modelo

`Movimiento` guarda id, fecha, tipo, categoría, descripción y monto. `TipoMovimiento` solo contiene las opciones `INGRESO` y `GASTO`.

### Datos

`MovimientoRepositorio` declara tres acciones: listar, agregar y eliminar. `ArchivoMovimientoRepositorio` realiza esas acciones usando el archivo `datos/movimientos.csv`.

### Servicio

`FinanzasServicio` valida los datos, asigna ids y calcula ingresos, gastos y saldo. No sabe cómo funciona el CSV.

### Vista

`VentanaPrincipal` muestra campos, botones, totales y una tabla. Cuando el usuario pulsa un botón, llama al servicio.

### Inicio

`ClarusFinanceApp` crea los objetos y abre la ventana.

## SOLID sin complicarlo

### S - Responsabilidad única

La ventana muestra información, el servicio calcula y el repositorio guarda. Ninguna clase intenta hacer todo.

### O - Abierto y cerrado

Podría agregarse una clase `BaseDatosMovimientoRepositorio` sin modificar `FinanzasServicio`.

### L - Sustitución de Liskov

Cualquier repositorio que implemente las tres operaciones puede usarse en lugar del repositorio de archivo.

### I - Segregación de interfaces

La interfaz tiene únicamente las operaciones que la aplicación necesita.

### D - Inversión de dependencias

El servicio recibe `MovimientoRepositorio` en su constructor. Por eso depende de una idea general y no directamente del CSV.

## Clean code usado

- Nombres en español y relacionados con la función.
- Métodos cortos como `registrar`, `saldo` y `eliminar`.
- Validaciones en un solo lugar.
- Constantes del tipo de movimiento en un `enum`.
- Sin contraseñas ni datos privados dentro del código.

El objetivo no es demostrar una arquitectura empresarial, sino aplicar buenas prácticas básicas en un proyecto que un estudiante pueda comprender completo.
