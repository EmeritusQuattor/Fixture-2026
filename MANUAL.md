# Manual de Uso — Mundial 2026 Fixture

## Requisitos

1. **Java JDK 24 o superior** (el proyecto fue desarrollado con OpenJDK 26)
   - Verificar con: `java -version`
   - Descargar de: https://jdk.java.net/

2. **JavaFX SDK 24+** (compatible con la versión de JDK)
   - Descargar de: https://openjfx.io/  o  https://jdk.java.net/javafx26/
   - Buscar "Windows x64 SDK zip"

3. **SQLite JDBC Driver** — ya incluido en `lib/sqlite-jdbc-3.53.2.0.jar`

---

## Estructura del proyecto

```
WC2026Fixture/
├── lib/
│   └── sqlite-jdbc-3.53.2.0.jar
├── src/main/java/com/mundial/
│   ├── Main.java                ← Punto de entrada (JavaFX)
│   ├── db/
│   │   ├── ConnectionManager.java
│   │   └── SchemaInitializer.java
│   ├── model/
│   │   ├── Group.java
│   │   ├── Team.java
│   │   └── Match.java
│   └── dao/
│       ├── CrudDAO.java
│       ├── GroupDAO.java
│       ├── TeamDAO.java
│       └── MatchDAO.java
├── target/classes/              ← Compilados (se genera solo)
├── mundial2026.db               ← Base de datos (se genera sola)
├── pom.xml
├── run.bat                      ← Script lista para Windows
└── MANUAL.md
```

---

## Configuración inicial

### 1. Ubicar las rutas del JDK y JavaFX

Identificá dónde tenés instalados:

- **JDK**: ej. `C:\Users\tuUsuario\.jdks\openjdk-26.0.1\`
- **JavaFX SDK**: ej. `C:\Users\tuUsuario\Downloads\openjfx-26.0.1_windows-x64_bin-sdk\javafx-sdk-26.0.1\`

### 2. Configurar `run.bat`

Abrí `run.bat` con un editor de texto (Bloc de notas) y actualizá las primeras líneas:

```batch
set JAVA_HOME=C:\ruta\a\tu\jdk
set JAVAFX_HOME=C:\ruta\a\tu\javafx-sdk
```

Guardá los cambios.

---

## Cómo ejecutar

### Opción 1: Con `run.bat` (recomendado)

Hacé doble clic en `run.bat` o ejecutalo desde terminal:

```
.\run.bat
```

### Opción 2: Manual (Paso a paso)

#### Compilar

```batch
"%JAVA_HOME%\bin\javac" --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml -cp "lib\*" -d "target\classes" -sourcepath "src\main\java" "src\main\java\com\mundial\Main.java"
```

Reemplazar `%JAVA_HOME%` y `%JAVAFX_HOME%` por las rutas reales.

#### Ejecutar

```batch
"%JAVA_HOME%\bin\java" --module-path "%JAVAFX_HOME%\lib" --add-modules javafx.controls,javafx.fxml -cp "target\classes;lib\*" com.mundial.Main
```

---

## Uso de la aplicación

Al iniciar, la base de datos se puebla automáticamente con los **12 grupos** y **48 selecciones** del Mundial 2026.

### Pestañas

#### 1. Grupos y Equipos
Muestra los 12 grupos (A–L) con sus 4 equipos cada uno.

#### 2. Fixture
Muestra los partidos cargados (vacío inicialmente).

#### 3. Resultados
Formulario para cargar resultados de partidos:
1. Seleccionar equipo **Local** y **Visitante**
2. Ingresar **goles** de cada uno
3. Seleccionar **Fase** (Grupo A–L, 16avos, Octavos, Cuartos, Semis, Final)
4. Ingresar **Fecha** (formato: `YYYY-MM-DD`)
5. Click en **Guardar Partido**

Los partidos guardados aparecen en la pestaña **Fixture**.

---

## Ver la base de datos

Podés abrir `mundial2026.db` con **SQLite Studio** (descargar de https://sqlitestudio.pl/) para explorar los datos directamente.

---

## Solución de problemas

| Problema | Causa probable | Solución |
|----------|---------------|----------|
| `java: command not found` | JDK no instalado o no en PATH | Instalar JDK 24+ y/o agregarlo al PATH |
| `module javafx.controls not found` | Ruta de JavaFX incorrecta | Verificar `JAVAFX_HOME` en `run.bat` |
| `database is locked` | DB abierta en SQLite Studio | Cerrar SQLite Studio |
| La tabla no muestra datos | Base de datos vacía o antigua | Borrar `mundial2026.db` y re-ejecutar |
| `class file has wrong version` | JDK incompatible con JavaFX | Usar JDK y JavaFX de la misma versión |
