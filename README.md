# Clarus Finance

Aplicación de escritorio para administrar finanzas personales con Java Swing y PostgreSQL. Permite iniciar sesión, registrar ingresos y gastos, editar o eliminar movimientos, consultar un dashboard mensual, controlar presupuestos por categoría y exportar reportes CSV.

## Repositorio

URL preparada para publicación: <https://github.com/AntoineMarcel07-commits/clarus-finance>

> El proyecto incluye historial Git local y etiquetas de versión. Si la URL todavía no abre, consulta `docs/VERSIONADO.md` para publicarlo desde la cuenta correspondiente.

## Funcionalidades

- Inicio de sesión con contraseñas protegidas mediante PBKDF2.
- Dashboard mensual de ingresos, gastos, balance y uso del presupuesto.
- Alta, consulta, edición y eliminación de ingresos y gastos.
- Presupuestos mensuales por categoría con estados disponible, por alcanzar y excedido.
- Exportación de movimientos a CSV.
- Creación automática del esquema y datos de demostración.
- Configuración de base de datos externa al código.
- 18 pruebas unitarias independientes de PostgreSQL.

## Requisitos

- JDK 17 o superior.
- Apache Maven 3.9 o NetBeans con soporte Maven.
- PostgreSQL 14 o superior.

## Instalación rápida

1. Crea en PostgreSQL una base llamada `clarus_finance`. Puedes ejecutar `database/create_database.sql` desde pgAdmin.
2. Copia `config/application.properties.example` como `config/application.properties`.
3. Edita `application.properties` con tu usuario y contraseña de PostgreSQL.
4. Abre esta carpeta desde **File > Open Project** en NetBeans.
5. Ejecuta `com.clarusfinance.ClarusFinanceApp`.

La primera ejecución crea las tablas y carga datos de demostración. Credenciales de la app:

- Usuario: `admin`
- Contraseña: `Clarus123!`

La guía ilustrada y la solución de problemas están en `docs/Manual_Usuario_Clarus_Finance.pdf`.

## Compilar y probar

```bash
mvn clean test
mvn package
java -jar target/ClarusFinance.jar
```

También puedes configurar sin archivo usando las variables `CLARUS_DB_URL`, `CLARUS_DB_USER` y `CLARUS_DB_PASSWORD`.

## Arquitectura

El proyecto separa responsabilidades en cuatro áreas:

```text
ui -> application -> domain
             ^          ^
             |          |
       infrastructure --+
```

- `domain`: entidades y contratos, sin Swing ni JDBC.
- `application`: casos de uso y reglas de negocio.
- `infrastructure`: PostgreSQL, configuración y seguridad.
- `ui`: ventanas, paneles y diálogos Swing.

Esta organización aplica inversión de dependencias: los servicios conocen interfaces de repositorio y el arranque conecta las implementaciones JDBC. La explicación completa y la relación con SOLID están en `docs/ARQUITECTURA.md`.

## Estructura

```text
ClarusFinance/
├── config/
├── database/
├── docs/
├── src/main/java/com/clarusfinance/
│   ├── application/
│   ├── domain/
│   ├── infrastructure/
│   └── ui/
├── src/main/resources/db/
├── src/test/java/
└── pom.xml
```

## Versiones

- `v0.1.0`: dominio, casos de uso y persistencia PostgreSQL.
- `v0.2.0`: experiencia completa de escritorio Swing.
- `v0.3.0`: pruebas unitarias y corrección de límites de presupuesto.
- `v1.0.0`: documentación, manual y entrega estable.

Consulta `CHANGELOG.md` y `docs/VERSIONADO.md`.

## Seguridad y alcance

- `config/application.properties` está excluido de Git para evitar publicar contraseñas de PostgreSQL.
- Las consultas usan `PreparedStatement`.
- La contraseña de demostración no se almacena en texto plano.
- Es una aplicación educativa de finanzas personales; no ofrece asesoría financiera ni sincronización bancaria.

## Licencia

MIT. Consulta `LICENSE`.
