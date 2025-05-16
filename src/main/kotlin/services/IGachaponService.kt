package es.prog2425.prueba.services

import es.prog2425.prueba.model.Gachapon

interface IGachaponService {
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos(): List<Gachapon>
}