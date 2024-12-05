package com.alura.Liter_Alura.services;

import com.alura.Liter_Alura.model.Libro;
import com.alura.Liter_Alura.repository.LibroRepository;

import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;
import java.util.List;

@Service
public class EstadisticasService {

    private final LibroRepository repositorio;

    public EstadisticasService(LibroRepository repositorio) {
        this.repositorio = repositorio;
    }

    // Método que genera las estadísticas de descargas
    public DoubleSummaryStatistics generarEstadisticas() {
        List<Libro> libros = repositorio.findAll();

        if (libros.isEmpty()) {
            throw new IllegalStateException("No hay libros registrados para generar estadísticas.");
        }

        return libros.stream()
                .mapToDouble(Libro::getNumeroDeDescargas)
                .summaryStatistics();
    }
}
