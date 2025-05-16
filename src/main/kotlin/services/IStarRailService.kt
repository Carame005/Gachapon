package es.prog2425.prueba.services

import es.prog2425.prueba.model.StarRail

interface IStarRailService {
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos(): List<StarRail>
    fun obtenerPity() : Int
    fun truncarTabla()
}