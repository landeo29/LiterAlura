package com.alura.Liter_Alura.repository;

import com.alura.Liter_Alura.model.Idioma;
import com.alura.Liter_Alura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByIdioma(Idioma idioma);

    // Método para obtener los 10 libros más descargados
    @Query("SELECT l FROM Libro l ORDER BY l.numeroDeDescargas DESC")
    List<Libro> findTop10LibrosMasDescargados();
}
