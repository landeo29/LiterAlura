package com.alura.Liter_Alura.principal;

import com.alura.Liter_Alura.model.*;
import com.alura.Liter_Alura.repository.AutorRepository;
import com.alura.Liter_Alura.repository.LibroRepository;
import com.alura.Liter_Alura.services.ConsumoAPI;
import com.alura.Liter_Alura.services.ConvierteDatos;
import com.alura.Liter_Alura.services.EstadisticasService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Principal {

    private final Scanner teclado = new Scanner(System.in);
    private final ConsumoAPI consumoAPI;
    private final ConvierteDatos conversor;
    private final LibroRepository repositorio;
    private final AutorRepository autorRepositorio;
    private final EstadisticasService estadisticasService;

    private static final String URL_BASE = "http://gutendex.com/books/";
    private static final String PARAM_SEARCH = "?search=";

    @Autowired
    public Principal(LibroRepository repository, AutorRepository autorRepository, EstadisticasService estadisticasService) {
        this.repositorio = repository;
        this.autorRepositorio = autorRepository;
        this.consumoAPI = new ConsumoAPI();
        this.conversor = new ConvierteDatos(new ObjectMapper());
        this.estadisticasService = estadisticasService;
    }

    public void muestraMenu() {
        int opcion = 0;
        while (opcion != 10) {
            try {
                var menu = """
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
                    """;
                System.out.println(menu);
                opcion = teclado.nextInt();
                teclado.nextLine();

                switch (opcion) {
                    case 1 -> getLibroPorTitulo();
                    case 2 -> getLibrosRegistrados();
                    case 3 -> getAutoresRegistrados();
                    case 4 -> getAutoresPorAnio();
                    case 5 -> getLibrosPorIdioma();
                    case 6 -> generarEstadisticas();
                    case 7 -> mostrarTop10LibrosMasDescargados();
                    case 8 -> buscarAutorPorNombre();
                    case 9 -> listarAutoresPorFecha();
                    case 10 -> System.out.println("Hasta luego. Cerrando aplicación...");
                    default -> System.out.println("Opción inválida");
                }

            } catch (InputMismatchException e) {
                System.out.println("Error: Entrada no válida. Por favor, ingrese un número entero entre 1 y 9.");
                teclado.next();
            }
        }
    }

    private void buscarAutorPorNombre() {
        System.out.println("Ingrese el nombre del autor que desea buscar:");
        String nombreAutor = teclado.nextLine().trim();

        List<Autor> autoresEncontrados = autorRepositorio.findByNombreContainingIgnoreCase(nombreAutor);

        if (!autoresEncontrados.isEmpty()) {
            System.out.println("Autores encontrados:");
            autoresEncontrados.forEach(autor -> {
                System.out.println("--------------------");
                System.out.println(autor);
                System.out.println("--------------------");
            });
        } else {
            System.out.println("No se encontraron autores con ese nombre.");
        }
    }

    private void mostrarTop10LibrosMasDescargados() {
        System.out.println("******* Top 10 Libros Más Descargados *******");
        List<Libro> top10Libros = repositorio.findTop10LibrosMasDescargados();

        if (!top10Libros.isEmpty()) {
            top10Libros.forEach(libro -> {
                System.out.printf("""
                        Título: %s
                        Autor: %s
                        Idioma: %s
                        Descargas: %d
                        -------------------------------
                        """, libro.getTitulo(), libro.getAutor().getNombre(), libro.getIdioma(), libro.getNumeroDeDescargas());
            });
        } else {
            System.out.println("No se encontraron libros para mostrar.");
        }
    }

    private void generarEstadisticas() {
        System.out.println("******* Estadísticas Generadas *******");

        try {
            DoubleSummaryStatistics estadisticas = estadisticasService.generarEstadisticas();

            System.out.printf("""
                Total de descargas: %.0f
                Promedio de descargas: %.2f
                Máximo de descargas: %.0f
                Mínimo de descargas: %.0f
                Libros analizados: %d
                -------------------------------
                """,
                    estadisticas.getSum(),
                    estadisticas.getAverage(),
                    estadisticas.getMax(),
                    estadisticas.getMin(),
                    estadisticas.getCount()
            );
        } catch (IllegalStateException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void getLibroPorTitulo() {
        System.out.println("Ingrese el nombre del libro que desea buscar:");
        String nombreLibro = teclado.nextLine();
        String json = consumoAPI.obtenerDatos(URL_BASE + PARAM_SEARCH + nombreLibro.replace(" ", "%20"));
        DatosBookObject datosBookObject = conversor.obtenerDatos(json, DatosBookObject.class);

        Optional<DatosLibro> libroEncontrado = datosBookObject.libros().stream().findFirst();

        if (libroEncontrado.isPresent()) {
            DatosLibro libro = libroEncontrado.get();
            String autor = libro.autor().stream().map(DatosAutor::nombre).limit(1).collect(Collectors.joining());
            String idioma = libro.idioma().stream().limit(1).collect(Collectors.joining());

            System.out.printf("""
                
            ---------- Libro Encontrado ----------
            Título: %s
            Autor: %s
            Idioma: %s
            Descargas: %d
            -------------------------------
            """, libro.titulo(), autor, idioma, libro.numeroDeDescargas());

            guardaLibroBaseDatos(libro);
        } else {
            System.out.println("No se encontró el libro con el título proporcionado.");
        }
    }

    private void guardaLibroBaseDatos(DatosLibro libroEncontrado) {
        try {
            Libro libro = new Libro(libroEncontrado);
            Autor autor = new Autor(libroEncontrado.autor().get(0));
            Optional<Autor> autorExistente = autorRepositorio.findByNombre(autor.getNombre());
            if (autorExistente.isPresent()) {
                libro.setAutor(autorExistente.get());
            } else {
                autorRepositorio.save(autor);
                libro.setAutor(autor);
            }
            repositorio.save(libro);
            System.out.println("Libro guardado exitosamente en la base de datos.");
        } catch (Exception e) {
            System.out.println("Error: No se puede registrar el libro más de una vez.");
        }
    }

    private void getLibrosRegistrados() {
        System.out.println("******* Libros Registrados *******");
        List<Libro> libros = repositorio.findAll();
        if (libros.isEmpty()) {
            System.out.println("No se han registrado libros aún.");
        } else {
            libros.forEach(libro -> {
                System.out.println("--------------------");
                System.out.println(libro);
                System.out.println("--------------------");
            });
        }
    }

    private void getAutoresRegistrados() {
        System.out.println("******* Autores Registrados *******");
        List<Autor> autores = autorRepositorio.findAll();
        if (autores.isEmpty()) {
            System.out.println("No se han registrado autores aún.");
        } else {
            autores.forEach(autor -> {
                System.out.println("--------------------");
                System.out.println(autor);
                System.out.println("--------------------");
            });
        }
    }

    private void getAutoresPorAnio() {
        System.out.println("Ingrese el año para verificar autores vivos en ese año:");
        int anio = teclado.nextInt();
        teclado.nextLine();
        List<Autor> autores = autorRepositorio.getAutoresPorAnio(anio);
        if (!autores.isEmpty()) {
            autores.forEach(System.out::println);
        } else {
            System.out.println("No hay autores vivos en ese año.");
        }
    }

    private void getLibrosPorIdioma() {
        System.out.println("Ingrese el idioma que desea (ejemplo: 'es' para español, 'en' para inglés, etc.):");
        String idioma = teclado.nextLine().trim().toLowerCase();

        Optional.ofNullable(Idioma.fromString(idioma))
                .map(repositorio::findByIdioma)
                .ifPresentOrElse(libros -> {
                    System.out.println("Libros en el idioma " + idioma + ":");
                    libros.forEach(System.out::println);
                }, () -> System.out.println("No se encontraron libros para ese idioma."));
    }

    private void listarAutoresPorFecha() {
        System.out.println("""
            Seleccione una consulta para los autores:
            1 - Autores nacidos después de un año específico.
            2 - Autores nacidos antes de un año específico.
            3 - Autores nacidos entre dos años.
            4 - Autores fallecidos antes de un año específico.
            5 - Autores vivos en un año específico.
            6 - Regresar al menú principal.
            """);

        int opcion = teclado.nextInt();
        teclado.nextLine();

        switch (opcion) {
            case 1 -> {
                System.out.println("Ingrese el año para buscar autores nacidos después de ese año:");
                int anio = teclado.nextInt();
                teclado.nextLine();
                autorRepositorio.findByFechaNacimientoAfter(anio).forEach(System.out::println);
            }
            case 2 -> {
                System.out.println("Ingrese el año para buscar autores nacidos antes de ese año:");
                int anio = teclado.nextInt();
                teclado.nextLine();
                autorRepositorio.findByFechaNacimientoBefore(anio).forEach(System.out::println);
            }
            case 3 -> {
                System.out.println("Ingrese el rango de años (ejemplo: 1900-1950):");
                String rango = teclado.nextLine();
                String[] fechas = rango.split("-");
                int desde = Integer.parseInt(fechas[0]);
                int hasta = Integer.parseInt(fechas[1]);
                autorRepositorio.findByFechaNacimientoBetween(desde, hasta).forEach(System.out::println);
            }
            case 4 -> {
                System.out.println("Ingrese el año para buscar autores fallecidos antes de ese año:");
                int anio = teclado.nextInt();
                teclado.nextLine();
                autorRepositorio.findByFechaFallecimientoBefore(anio).forEach(System.out::println);
            }
            case 5 -> {
                System.out.println("Ingrese el año para buscar autores vivos en ese año:");
                int anio = teclado.nextInt();
                teclado.nextLine();
                autorRepositorio.getAutoresPorAnio(anio).forEach(System.out::println);
            }
            case 6 -> System.out.println("Regresando al menú principal...");
            default -> System.out.println("Opción no válida.");
        }
    }

}
