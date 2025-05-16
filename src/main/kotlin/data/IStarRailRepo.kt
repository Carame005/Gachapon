package es.prog2425.prueba.data

import es.prog2425.prueba.model.StarRail

interface IStarRailRepo {
    fun crearTabla()
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos() : List<StarRail>
    fun obtenerPity() : Int
    fun truncarTabla()
}