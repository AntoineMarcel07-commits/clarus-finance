# Clarus Finance - Manual de usuario e instalación

Versión 1.0.0 - 13 de agosto de 2026

## 1. Acerca del sistema

Clarus Finance es una aplicación de escritorio para registrar ingresos y gastos, consultar el balance mensual, controlar límites por categoría y exportar movimientos. Está pensada como proyecto educativo y no se conecta con bancos.

## 2. Requisitos

- Windows, macOS o Linux con entorno gráfico.
- JDK 17 o posterior.
- PostgreSQL 14 o posterior.
- NetBeans 20 o posterior, recomendado, o Maven 3.9.
- Aproximadamente 100 MB libres.

## 3. Instalación

### 3.1 Instalar Java y PostgreSQL

Instala un JDK 17 y PostgreSQL. Conserva el usuario, contraseña y puerto elegidos. El puerto normal de PostgreSQL es 5432.

### 3.2 Crear la base de datos

En pgAdmin, crea una base llamada exactamente `clarus_finance`. Como alternativa, ejecuta `database/create_database.sql` con un usuario autorizado.

### 3.3 Configurar la conexión

Dentro del proyecto, copia `config/application.properties.example` como `config/application.properties`. Cambia los tres valores según tu instalación:

```properties
db.url=jdbc:postgresql://localhost:5432/clarus_finance
db.user=postgres
db.password=TU_CONTRASENA
```

El archivo real está ignorado por Git para proteger la contraseña.

### 3.4 Abrir y ejecutar en NetBeans

1. Selecciona **File > Open Project**.
2. Elige la carpeta `ClarusFinance`.
3. Espera a que Maven descargue las dependencias.
4. Ejecuta `com.clarusfinance.ClarusFinanceApp`.

La primera ejecución crea las tablas y los datos iniciales automáticamente.

### 3.5 Ejecutar el JAR

Compila con `mvn clean package` y ejecuta `java -jar target/ClarusFinance.jar` desde la raíz del proyecto para que pueda localizar `config/application.properties`.

## 4. Iniciar sesión

Usa `admin` y `Clarus123!`. Si PostgreSQL no está disponible, la app muestra un mensaje con los pasos que debes revisar.

## 5. Dashboard

El dashboard presenta ingresos, gastos, balance, porcentaje global de presupuesto y los ocho movimientos más recientes. Cambia el periodo y pulsa **Actualizar**.

## 6. Movimientos

### Registrar

Pulsa **Nuevo movimiento**, elige ingreso o gasto, escribe un monto mayor a cero, selecciona la categoría y confirma la fecha. La fecha no puede estar en el futuro.

### Editar o eliminar

Selecciona una fila y usa **Editar** o **Eliminar**. La eliminación solicita confirmación.

### Exportar

Selecciona el mes, pulsa **Exportar CSV** y elige una carpeta. El CSV se abre en Excel, LibreOffice o Google Sheets.

## 7. Presupuestos

Pulsa **Nuevo presupuesto**, elige categoría, límite y mes. Solo existe un presupuesto por categoría y periodo.

- Disponible: consumo menor al 80%.
- Por alcanzar: consumo entre 80% y 100%.
- Excedido: gasto superior al límite.

## 8. Copias de seguridad

Desde pgAdmin, selecciona la base, elige **Backup** y guarda el archivo. Para restaurar, crea una base vacía y usa **Restore**. Haz una copia antes de cambios importantes.

## 9. Solución de problemas

- **No se pudo conectar**: confirma que PostgreSQL está iniciado, la base se llama `clarus_finance` y el archivo de configuración contiene el usuario y contraseña correctos.
- **Puerto rechazado**: revisa si PostgreSQL usa un puerto distinto de 5432 y modifica `db.url`.
- **Dependencias Maven**: verifica internet, selecciona **Reload Project** y vuelve a compilar.
- **Usuario incorrecto**: respeta mayúsculas de la contraseña y usa `admin`.
- **Presupuesto duplicado**: edita el existente o cambia categoría o periodo.
- **CSV no abre**: importa el archivo como UTF-8 y separador coma.

## 10. Pruebas y versiones

Ejecuta `mvn clean test`. La versión 1.0.0 incluye 18 pruebas, sin fallos. Usa `git log --oneline --decorate` y `git tag --list` para revisar el historial.

Repositorio preparado: <https://github.com/AntoineMarcel07-commits/clarus-finance>

## 11. Buenas prácticas

- No publiques `config/application.properties`.
- Usa una contraseña distinta de la demo si la app se usa fuera de clase.
- Haz respaldos de PostgreSQL.
- Registra movimientos con categorías consistentes para que los presupuestos sean exactos.
- Conserva el historial Git y crea una nueva etiqueta para cada entrega.
