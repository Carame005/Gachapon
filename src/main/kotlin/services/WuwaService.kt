package es.prog2425.prueba.services

import es.prog2425.prueba.data.WuwaRepo
import es.prog2425.prueba.model.Wuwa

class WuwaService(val repo: WuwaRepo) : IWuwaService {
    override fun insertarValores(calidad: String, nombre: String) {
        repo.insertarValores(calidad,nombre)
    }

    override fun obtenerTodos(): List<Wuwa> {
        return repo.obtenerTodos()
    }

    override fun obtenerPity(): Int {
        return repo.obtenerPity()
    }

    override fun truncarTabla() {
        repo.truncarTabla()
    }
}