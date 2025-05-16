package es.prog2425.prueba.services

import es.prog2425.prueba.data.IGachaponRepo
import es.prog2425.prueba.model.Gachapon

class GachaponService(val repo : IGachaponRepo) : IGachaponService {
    override fun insertarValores(calidad: String, nombre: String) {
        repo.insertarValores(calidad,nombre)
    }

    override fun obtenerTodos() : List<Gachapon> {
        return repo.obtenerTodos()
    }
}