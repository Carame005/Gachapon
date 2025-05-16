package es.prog2425.prueba.services

import es.prog2425.prueba.model.Wuwa

interface IWuwaService {
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos(): List<Wuwa>
    fun obtenerPity() : Int
    fun truncarTabla()
}