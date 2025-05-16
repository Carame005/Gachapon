package es.prog2425.prueba.data

import es.prog2425.prueba.model.Wuwa

interface IWuwaRepo {
    fun crearTabla()
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos() : List<Wuwa>
    fun obtenerPity() : Int
    fun truncarTabla()
}