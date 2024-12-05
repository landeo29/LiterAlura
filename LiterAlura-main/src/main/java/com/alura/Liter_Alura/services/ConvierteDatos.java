package com.alura.Liter_Alura.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConvierteDatos implements IConvierteDatos {

    private static final Logger logger = Logger.getLogger(ConvierteDatos.class.getName());
    private final ObjectMapper objectMapper;

    // Constructor para inyectar ObjectMapper (permite personalización y testing)
    public ConvierteDatos(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper != null ? objectMapper : new ObjectMapper(); // Usar ObjectMapper predeterminado si no se proporciona uno
    }

    @Override
    public <T> T obtenerDatos(String json, Class<T> clase) {
        try {
            logger.info("Iniciando la conversión de JSON a objeto de clase: " + clase.getName());
            T result = objectMapper.readValue(json, clase);
            logger.info("Conversión exitosa.");
            return result;
        } catch (JsonProcessingException e) {
            logger.log(Level.SEVERE, "Error durante la conversión de JSON a objeto. JSON recibido: " + json, e);
            throw new JsonConversionException("Error al convertir el JSON a un objeto de tipo " + clase.getName(), e);
        }
    }
}
