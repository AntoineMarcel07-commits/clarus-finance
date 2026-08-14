# Arquitectura básica

## Estructura

```text
ClarusFinance
      |
      v
VentanaPrincipal
      |
      v
OperacionesFinanzas
      |
      v
Finanzas ----> ArrayList<Movimiento>
```

## Clases

### ClarusFinance

Es la clase `main`. Crea un objeto `Finanzas`, crea la ventana y la muestra. Sigue el mismo estilo usado en `AmazonMx`.

### Movimiento

Tiene campos públicos sencillos:

- `id`
- `fecha`
- `tipo`
- `categoria`
- `descripcion`
- `monto`

El método `InfoMovimiento` crea y devuelve un movimiento. Es el mismo patrón usado en `InfoClientes`, `InfoPedidos` e `InfoProductos` de otros proyectos.

### Finanzas

Contiene un `ArrayList<Movimiento>` y métodos directos para agregar, eliminar y calcular totales.

### OperacionesFinanzas

Es una interfaz pequeña. Funciona como una lista de métodos que la clase `Finanzas` debe tener. Se conserva solamente para demostrar SOLID de forma sencilla.

### VentanaPrincipal

Es un JFrame. Lee los campos cuando se pulsa **AGREGAR**, muestra el `ArrayList` en una tabla y llama a los métodos de `Finanzas`.

## SOLID explicado fácil

- **Responsabilidad única:** `Movimiento` guarda datos, `Finanzas` calcula y la ventana muestra.
- **Abierto/cerrado:** se puede crear otra clase con las mismas operaciones sin cambiar la interfaz.
- **Sustitución:** otra implementación de `OperacionesFinanzas` puede ocupar el lugar de `Finanzas`.
- **Segregación:** la interfaz solo contiene las seis operaciones que usa la ventana.
- **Inversión de dependencias:** la ventana recibe `OperacionesFinanzas` en el constructor.

## Clean code

- Nombres sencillos en español.
- Métodos cortos: `agregar`, `eliminar`, `saldo`.
- Una sola lista de movimientos.
- Validación simple del monto y los textos.
- Sin contraseñas dentro del código.

Esta arquitectura cumple la rúbrica sin intentar parecer un sistema profesional o empresarial.
