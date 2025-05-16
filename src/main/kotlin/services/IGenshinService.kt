package es.prog2425.prueba.services

import es.prog2425.prueba.model.Genshin

interface IGenshinService {
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos(): List<Genshin>
    fun obtenerPity() : Int
    fun truncarTabla()
}