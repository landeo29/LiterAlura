package com.alura.Liter_Alura.model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;

    private Long numeroDeDescargas;

    @ManyToOne
    private Autor autor;


    public Libro() {}

    public Libro(DatosLibro datosLibro) {
        if (datosLibro != null) {
            this.titulo = datosLibro.titulo();
            this.idioma = parseIdioma(datosLibro.idioma());
            this.numeroDeDescargas = datosLibro.numeroDeDescargas();
        }
    }

    // Método para convertir una lista de idiomas a un único valor de Idioma
    private Idioma parseIdioma(List<String> idiomas) {

        if (idiomas != null && !idiomas.isEmpty()) {
            return Idioma.fromString(idiomas.get(0));
        }
        return null;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public Long getNumeroDeDescargas() {
        return numeroDeDescargas;
    }

    public void setNumeroDeDescargas(Long numeroDeDescargas) {
        this.numeroDeDescargas = numeroDeDescargas;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        return String.format(
                "Libro [id=%d, titulo='%s', idioma='%s', numeroDeDescargas=%d, autor='%s']",
                id, titulo, idioma, numeroDeDescargas, (autor != null ? autor.getNombre() : "Desconocido"));
    }
}
