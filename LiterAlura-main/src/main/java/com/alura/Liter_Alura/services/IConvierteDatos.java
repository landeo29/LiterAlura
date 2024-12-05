package com.alura.Liter_Alura.services;

public interface IConvierteDatos {
    //Método que transforma un json en una clase, trabaja con datos tipo genérico para que sea más escalable
    <T> T obtenerDatos(String json, Class<T> clase);
}
