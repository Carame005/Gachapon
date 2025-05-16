package es.prog2425.prueba.services

import es.prog2425.prueba.model.Zenless

interface IZenlessService {
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos(): List<Zenless>
    fun obtenerPity() : Int
    fun truncarTabla()
}