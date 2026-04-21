# 📺 Análisis de Series con JSON (Java)

Este proyecto es una aplicación en Java que permite analizar un conjunto de datos de series de televisión almacenadas en un archivo JSON.

## 📂 Estructura del proyecto

* `tvs.json` → archivo principal con las series
* `languages.json` → relación de códigos de idioma a nombres
* `tvs_modificat.json` → archivo generado con datos modificados
* `App.java` → programa principal

## ⚙️ Funcionalidades

El programa ofrece un menú con las siguientes opciones:

1. Cargar archivo JSON
2. Contar número de series
3. Mostrar nombre y nombre original
4. Mostrar idioma original
5. Mostrar país de origen
6. Mostrar géneros
7. Mostrar información de *Breaking Bad*
8. Mostrar serie con mejor puntuación
9. Mostrar serie con peor puntuación
10. Mostrar serie con más episodios
11. Cambiar códigos de idioma a nombres completos
12. Guardar los datos modificados en un nuevo JSON

## ▶️ Ejecución

1. Asegúrate de tener Java instalado
2. Compila el programa:

```
javac App.java
```

3. Ejecuta:

```
java App
```

## 📝 Notas

* El programa usa la librería `json-simple`
* Algunos campos del JSON pueden ser nulos, por lo que se controlan errores
* Los idiomas se transforman usando el archivo `languages.json`