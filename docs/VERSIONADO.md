# Versionado y publicación

## Repositorio objetivo

<https://github.com/AntoineMarcel07-commits/clarus-finance>

La entrega contiene un repositorio Git aislado. No usa el repositorio accidental ubicado en la carpeta personal y no incluye archivos ajenos.

## Historial

```text
v0.1.0  Núcleo, reglas y PostgreSQL
v0.2.0  Interfaz Swing completa
v0.3.0  Pruebas automáticas y corrección de presupuesto
v1.0.0  Documentación y entrega estable
```

Comandos útiles:

```bash
git log --oneline --decorate
git tag --list
git show v1.0.0
```

## Publicar en GitHub

1. Inicia sesión en GitHub y crea un repositorio vacío llamado `clarus-finance`.
2. Desde la raíz de este proyecto ejecuta:

```bash
git remote add origin https://github.com/AntoineMarcel07-commits/clarus-finance.git
git push -u origin main
git push origin --tags
```

Si el repositorio remoto ya está configurado, omite `git remote add origin`.

## Recuperar desde el bundle

El archivo `ClarusFinance.bundle` conserva commits y etiquetas aunque la carpeta se copie sin `.git`:

```bash
git clone ClarusFinance.bundle ClarusFinance
cd ClarusFinance
git switch main
```

## Estrategia

Se usa versionado semántico. Una versión mayor rompe compatibilidad, una menor añade funciones compatibles y un parche corrige defectos. Cada versión de entrega tiene una etiqueta anotada.
