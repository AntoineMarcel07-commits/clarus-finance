# Versionado con Git

El repositorio público es:

<https://github.com/AntoineMarcel07-commits/clarus-finance>

## Comandos básicos

```bash
git status
git add .
git commit -m "descripcion del cambio"
git push origin main
```

## Ver el historial

```bash
git log --oneline
git tag --list
```

## Versiones

- `v0.1.0`: primera estructura del proyecto.
- `v0.2.0`: interfaz de escritorio.
- `v0.3.0`: pruebas unitarias.
- `v1.0.0`: documentación y primera entrega.
- `v1.1.0`: versión simplificada, sin PostgreSQL y con una sola ventana.

Una etiqueta representa una entrega. Para crear otra versión:

```bash
git tag -a v1.2.0 -m "Version 1.2.0"
git push origin v1.2.0
```

Para una tarea escolar basta con explicar que Git guarda el historial, GitHub publica el repositorio y las etiquetas identifican las entregas.
