package es.prog2425.prueba.services

import es.prog2425.prueba.data.StarRailRepo
import es.prog2425.prueba.model.StarRail

class StarRailService(val repo: StarRailRepo) : IStarRailService {
    override fun insertarValores(calidad: String, nombre: String) {
        repo.insertarValores(calidad,nombre)
    }

    override fun obtenerTodos() : List<StarRail> {
        return repo.obtenerTodos()
    }

    override fun obtenerPity(): Int {
        return repo.obtenerPity()
    }

    override fun truncarTabla() {
        repo.truncarTabla()
    }
}