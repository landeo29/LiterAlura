package com.alura.Liter_Alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosBookObject(
        @JsonAlias("results") List<DatosLibro> libros
) {
    public DatosBookObject {
        if (libros == null || libros.isEmpty()) {
            throw new IllegalArgumentException("La lista de libros no puede ser nula o vacía");
        }
    }
}
