package es.prog2425.prueba.services

import es.prog2425.prueba.data.ZenlessRepo
import es.prog2425.prueba.model.Zenless

class ZenlessService(val repo: ZenlessRepo) : IZenlessService {
    override fun insertarValores(calidad: String, nombre: String) {
        repo.insertarValores(calidad,nombre)
    }

    override fun obtenerTodos(): List<Zenless> {
        return repo.obtenerTodos()
    }

    override fun obtenerPity(): Int {
        return repo.obtenerPity()
    }

    override fun truncarTabla() {
        repo.truncarTabla()
    }
}