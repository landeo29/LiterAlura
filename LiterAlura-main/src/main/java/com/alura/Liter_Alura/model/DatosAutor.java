package com.alura.Liter_Alura.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public record DatosAutor(
        @JsonAlias("name") String nombre,
        @JsonAlias("birth_year") Integer fechaNacimiento,
        @JsonAlias("death_year") Integer fechaFallecimiento
) {
    public DatosAutor {
        if (fechaNacimiento != null && fechaFallecimiento != null && fechaFallecimiento < fechaNacimiento) {
            throw new IllegalArgumentException("La fecha de fallecimiento no puede ser anterior a la fecha de nacimiento");
        }
    }

    // Método para verificar si el autor está vivo
    public boolean estaVivo() {
        return fechaFallecimiento == null;
    }

    public Integer calcularEdad() {
        if (fechaNacimiento != null) {
            int currentYear = java.time.Year.now().getValue();
            return currentYear - fechaNacimiento;
        }
        return null;
    }
}
