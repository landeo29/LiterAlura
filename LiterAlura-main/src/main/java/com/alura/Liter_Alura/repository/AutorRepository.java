package com.alura.Liter_Alura.repository;

import com.alura.Liter_Alura.model.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutorRepository extends JpaRepository<Autor, Long> {

    Optional<Autor> findByNombre(String nombre);

    @Query("SELECT a FROM Autor a WHERE a.fechaNacimiento <= :anio AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento > :anio)")
    List<Autor> getAutoresPorAnio(Integer anio);

    List<Autor> findByNombreContainingIgnoreCase(String nombreAutor);

    Iterable<Object> findByFechaNacimientoAfter(int anio);

    Iterable<Object> findByFechaNacimientoBefore(int anio);

    Iterable<Object> findByFechaNacimientoBetween(int desde, int hasta);

    Iterable<Object> findByFechaFallecimientoBefore(int anio);
}
