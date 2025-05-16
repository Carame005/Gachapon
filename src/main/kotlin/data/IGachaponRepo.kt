package es.prog2425.prueba.data

import es.prog2425.prueba.model.Gachapon

interface IGachaponRepo {
    fun crearTabla()
    fun insertarValores(calidad : String, nombre : String)
    fun obtenerTodos() : List<Gachapon>
}