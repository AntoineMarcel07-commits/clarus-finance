# Arquitectura y principios SOLID

## Objetivo

Clarus Finance se diseñó como una aplicación de escritorio mantenible. La interfaz no ejecuta SQL ni decide reglas financieras; coordina casos de uso y muestra resultados.

## Flujo de dependencias

```text
Swing UI ──> Servicios de aplicación ──> Interfaces del dominio
   ^                                          ^
   |                                          |
   └──────── Composición al iniciar ── Implementaciones JDBC
```

`ClarusFinanceApp` es la raíz de composición: crea las dependencias concretas y las inyecta por constructor. Esto mantiene cada clase enfocada y hace posible probar servicios con repositorios en memoria.

## Capas

### Dominio

- Modelos inmutables: `Movement`, `Budget`, `BudgetProgress`, `DashboardSummary` y `UserAccount`.
- Contratos: `MovementRepository`, `BudgetRepository` y `UserRepository`.
- No importa Swing, JDBC ni PostgreSQL.

### Aplicación

- `AuthService`: autenticación y validación de credenciales.
- `MovementService`: alta, edición, consulta y eliminación de movimientos.
- `BudgetService`: presupuestos y cálculo exacto de avance.
- `DashboardService`: totales mensuales y balance.

### Infraestructura

- Configuración desde archivo o variables de entorno.
- `ConnectionFactory` abstrae la creación de conexiones.
- Repositorios JDBC usan `PreparedStatement` y try-with-resources.
- `DatabaseInitializer` aplica esquema y datos idempotentes.
- `Pbkdf2PasswordHasher` protege contraseñas con salt aleatorio.

### Presentación

- `LoginFrame` y `MainFrame` controlan el ciclo de sesión.
- Paneles especializados para dashboard, movimientos y presupuestos.
- Diálogos independientes para capturar datos.
- Componentes de tema evitan colores, fuentes y estilos duplicados.

## Evidencia SOLID

### S - Responsabilidad única

Cada servicio atiende un caso de uso. La persistencia, la seguridad, la configuración y la presentación viven en clases distintas.

### O - Abierto/cerrado

Un nuevo repositorio, por ejemplo SQLite o una API, puede implementar los contratos del dominio sin cambiar los servicios. Nuevas vistas consumen los mismos casos de uso.

### L - Sustitución de Liskov

Las implementaciones JDBC y los repositorios en memoria de las pruebas respetan los mismos contratos y se sustituyen sin cambiar el comportamiento esperado de los servicios.

### I - Segregación de interfaces

Los contratos son pequeños y específicos. La autenticación no depende de operaciones de movimientos; cada repositorio expone únicamente lo necesario.

### D - Inversión de dependencias

Los servicios dependen de interfaces del dominio. Las clases JDBC dependen de esos mismos contratos y se conectan en la raíz de composición.

## Clean code aplicado

- Nombres expresivos y clases pequeñas.
- Modelos inmutables con validación de invariantes.
- Sin estado global ni conexión pública compartida.
- Dinero representado con `BigDecimal`, no `double`.
- Fechas con `java.time`.
- Recursos JDBC cerrados automáticamente.
- Errores de negocio separados de errores de acceso a datos.
- Credenciales de PostgreSQL fuera del repositorio.
- Una única fuente de verdad para tema visual y formato monetario.

## Esquema de datos

- `schema_versions`: versión aplicada del esquema.
- `app_users`: usuarios y hash de contraseña.
- `movements`: ingresos y gastos.
- `budgets`: límites mensuales únicos por categoría y periodo.

Las restricciones de base de datos refuerzan las mismas invariantes del dominio: montos positivos, tipos válidos, periodos válidos y unicidad de presupuestos.
