package com.alura.Liter_Alura.model;

public enum Idioma {
    ESPANOL("es"),
    INGLES("en"),
    FRANCES("fr"),
    PORTUGUES("pt");

    private final String codigo;

    Idioma(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public static Idioma fromString(String codigo) {
        for (Idioma idioma : Idioma.values()) {
            if (idioma.getCodigo().equalsIgnoreCase(codigo)) {
                return idioma;
            }
        }
        throw new IllegalArgumentException("Código de idioma no válido: " + codigo);
    }
}
