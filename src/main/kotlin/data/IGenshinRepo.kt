package es.prog2425.prueba.data

import es.prog2425.prueba.model.Genshin

interface IGenshinRepo {
    fun crearTabla()
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos() : List<Genshin>
    fun obtenerPity() : Int
    fun truncarTabla()
}