package es.prog2425.prueba.data

import es.prog2425.prueba.model.Zenless

interface IZenlessRepo {
    fun crearTabla()
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos() : List<Zenless>
    fun obtenerPity() : Int
    fun truncarTabla()
}