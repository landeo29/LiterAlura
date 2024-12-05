package com.alura.Liter_Alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibro(
        @JsonAlias("title") String titulo,
        @JsonAlias("authors") List<DatosAutor> autor,
        @JsonAlias("languages") List<String> idioma,
        @JsonAlias("download_count") Long numeroDeDescargas
) {
    public DatosLibro {
        if (titulo == null || titulo.isEmpty()) {
            throw new IllegalArgumentException("El título no puede ser nulo o vacío");
        }
        if (autor == null || autor.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un autor");
        }
        if (idioma == null || idioma.isEmpty()) {
            throw new IllegalArgumentException("Debe haber al menos un idioma");
        }
        if (numeroDeDescargas == null || numeroDeDescargas < 0) {
            throw new IllegalArgumentException("El número de descargas no puede ser negativo");
        }
    }
}
