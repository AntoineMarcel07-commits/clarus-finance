-- Abre Query Tool dentro de la base ClarusFinance y ejecuta todo el archivo.

DROP TABLE IF EXISTS presupuestos;
DROP TABLE IF EXISTS movimientos;

CREATE TABLE movimientos (
    id_movimiento SERIAL PRIMARY KEY,
    fecha DATE NOT NULL,
    tipo VARCHAR(10) NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    descripcion VARCHAR(100) NOT NULL,
    monto NUMERIC(10, 2) NOT NULL
);

CREATE TABLE presupuestos (
    id_presupuesto SERIAL PRIMARY KEY,
    categoria VARCHAR(50) NOT NULL UNIQUE,
    limite NUMERIC(10, 2) NOT NULL
);

-- Datos sencillos para probar la aplicación.
INSERT INTO movimientos(fecha, tipo, categoria, descripcion, monto)
VALUES
    (CURRENT_DATE, 'Ingreso', 'Trabajo', 'Pago semanal', 2500.00),
    (CURRENT_DATE, 'Gasto', 'Comida', 'Supermercado', 650.00),
    (CURRENT_DATE, 'Gasto', 'Transporte', 'Gasolina', 400.00);

INSERT INTO presupuestos(categoria, limite)
VALUES
    ('Comida', 1500.00),
    ('Transporte', 1000.00);
