# Pruebas unitarias

## Ejecución verificada

Fecha: 13 de agosto de 2026

```text
Tests run: 18, Failures: 0, Errors: 0, Skipped: 0
BUILD SUCCESS
```

Comando reproducible:

```bash
mvn clean test
```

## Alcance

| Clase | Casos cubiertos |
|---|---|
| `AuthServiceTest` | acceso correcto, usuario normalizado, contraseña incorrecta, usuario ausente/inactivo y campos vacíos |
| `MovementServiceTest` | alta, monto inválido, fecha futura, edición, edición inexistente, filtro mensual y eliminación |
| `BudgetServiceTest` | avance saludable, advertencia al 80%, exceso exacto y validación |
| `DashboardServiceTest` | ingresos, gastos, balance, uso del presupuesto y periodo vacío |
| `Pbkdf2PasswordHasherTest` | salt distinto, verificación, contraseña incorrecta y hash malformado |

## Diseño de pruebas

Los tests usan implementaciones en memoria de las interfaces de repositorio. Por eso verifican reglas de negocio de forma rápida y determinista, sin requerir PostgreSQL ni interfaz gráfica. Las pruebas de persistencia quedan separadas como posible suite de integración futura.

## Defecto detectado

La primera ejecución detectó que `1000.01 / 1000` se redondeaba antes de clasificar el presupuesto. Se corrigió la regla para comparar directamente el gasto y el límite con `BigDecimal`. La prueba evita que el defecto vuelva a aparecer.
