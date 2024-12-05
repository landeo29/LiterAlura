package com.alura.Liter_Alura.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class ConsumoAPI {

    private static final Logger logger = Logger.getLogger(ConsumoAPI.class.getName());
    private final HttpClient client = HttpClient.newHttpClient();

    public String obtenerDatos(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            logger.info("Enviando solicitud a la URL: " + url);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                logger.info("Respuesta exitosa: " + response.statusCode());
                return response.body();
            } else {
                logger.warning("Error al obtener respuesta. Código de estado: " + response.statusCode());
                throw new IOException("Error al obtener datos: Código de estado " + response.statusCode());
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            logger.log(Level.SEVERE, "Error al obtener datos de la API", e);
            throw new RuntimeException("Error al consumir la API: " + e.getMessage(), e);
        }
    }
}
