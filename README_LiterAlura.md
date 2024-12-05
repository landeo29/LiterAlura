# LiterAlura - Challenge Java 📚

Bienvenido a **LiterAlura**, un programa diseñado para gestionar una base de datos de libros y autores, mientras interactúa con la API **Gutendex** para ofrecer una experiencia de búsqueda de libros más rica y actualizada.

Este proyecto permite gestionar información sobre libros y autores, realizar consultas avanzadas y analizar estadísticas de descargas.

## 🚀 Descripción del Proyecto

**LiterAlura** es una aplicación que permite a los usuarios:
- **Buscar libros por título** y obtener detalles como el autor, idioma y número de descargas.
- **Listar libros registrados** en una base de datos local.
- **Listar autores registrados** en la base de datos.
- **Buscar autores por nombre**.
- **Generar estadísticas de descargas** para libros más populares.
- **Top 10 libros más descargados**.
- **Consultar autores nacidos o fallecidos en un rango de años**.

El sistema consume la API **Gutendex**, que ofrece una vasta colección de **libros de dominio público**. A través de esta API, el programa obtiene información actualizada sobre títulos, autores, y estadísticas de descargas para ofrecer consultas más precisas y enriquecidas.

## ✨ Características Principales

### 1. **Buscar libro por título**
Busca libros utilizando la API **Gutendex** y obtiene información detallada sobre el libro, como autor, idioma y el número de descargas.

### 2. **Listar libros registrados**
Muestra todos los libros registrados en la base de datos local del sistema.

### 3. **Listar autores registrados**
Muestra todos los autores registrados en la base de datos, permitiendo conocer sus obras y datos de interés.

### 4. **Buscar autor por nombre**
Permite buscar autores por su nombre y mostrar su información detallada.

### 5. **Generar estadísticas de descargas**
Genera estadísticas sobre las descargas de los libros registrados, proporcionando el total de descargas, promedio, máximo y mínimo.

### 6. **Top 10 libros más descargados**
Muestra los 10 libros más descargados, junto con su título, autor, idioma y número de descargas.

### 7. **Listar autores por fecha (nacimiento/fallecimiento)**
Permite realizar consultas avanzadas para listar autores nacidos o fallecidos antes, después o entre un rango de años.

## 🌐 Integración con la API **Gutendex**

Este proyecto se conecta a la API **Gutendex**, que proporciona acceso a una colección de **libros de dominio público**. Mediante solicitudes HTTP, el programa puede buscar libros por:
- Título
- Autor
- Idioma
- Número de descargas

La API **Gutendex** nos permite obtener información precisa y actualizada para enriquecer la base de datos local y hacer consultas dinámicas.

El código de consumo de la API está contenido en la clase **`ConsumoAPI`**, que maneja todas las solicitudes y el procesamiento de los datos obtenidos.


## 🎮 Menú Interactivo

La aplicación se maneja a través de un **menú interactivo** en la consola, que permite al usuario seleccionar entre diversas opciones para consultar libros y autores. Al seleccionar una opción, el sistema proporciona una serie de resultados y te permite realizar acciones adicionales.

```
-------------------
Elija la opción a través de su número:
1 - Buscar libro por título.
2 - Listar libros registrados.
3 - Listar autores registrados.
4 - Listar autores vivos en determinado año.
5 - Listar libros por idioma.
6 - Generar estadísticas de descargas.
7 - Top 10 libros más descargados.
8 - Buscar autor por nombre.
9 - Listar autores por fecha (nacimiento/fallecimiento).
10 - Salir.
--------------------
```

---

## 💡 Funcionalidad del Código

Este sistema utiliza tecnologías como **Java 11**, **Spring Boot** y **Maven** para crear una plataforma interactiva eficiente y fácil de usar. Algunas de las clases clave del sistema incluyen:

- **`Principal`**: La clase central que gestiona el menú interactivo y las opciones seleccionadas por el usuario.
- **`ConsumoAPI`**: Encargada de consumir la API para obtener información sobre libros y autores.
- **`EstadisticasService`**: Calcula y presenta estadísticas sobre las descargas de libros.
- **`AutorRepository`** y **`LibroRepository`**: Interfaces que interactúan con la base de datos para realizar operaciones CRUD sobre los libros y autores.

La lógica detrás de cada funcionalidad se ha diseñado para ser intuitiva y accesible a través de simples interacciones de consola.

---

## 🌍 ¡Explora, Descubre y Aprende!

**LiterAlura** no es solo una herramienta para gestionar libros y autores; es un espacio donde los amantes de la literatura pueden sumergirse en el mundo de los libros de dominio público, descubrir nuevos autores y analizar la popularidad de sus obras.

Ya sea que estés interesado en encontrar un libro específico, investigar sobre un autor o simplemente explorar nuevos títulos, **LiterAlura** es tu aliado perfecto para gestionar y explorar la literatura de manera eficiente y divertida. 📚✨

---

¡Disfruta de la experiencia LiterAlura! 🙌
