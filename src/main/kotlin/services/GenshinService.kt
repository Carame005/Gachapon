package es.prog2425.prueba.services

import es.prog2425.prueba.data.IGenshinRepo
import es.prog2425.prueba.model.Genshin

class GenshinService(val repo : IGenshinRepo) : IGenshinService {
    override fun insertarValores(calidad: String, nombre: String) {
        repo.insertarValores(calidad,nombre)
    }

    override fun obtenerTodos() : List<Genshin> {
        return repo.obtenerTodos()
    }

    override fun obtenerPity(): Int {
        return repo.obtenerPity()
    }

    override fun truncarTabla() {
        repo.truncarTabla()
    }
}