package es.prog2425.prueba.app

import es.prog2425.prueba.ui.IEntradaSalida

class GestorHSR(private val ui : IEntradaSalida) {

    fun menuHSR(){
        var proceso = true
        while (proceso) {
            ui.limpiarPantalla()
            ui.mostrar(
                """
                    ---Opciones---
                    1.Tirar 1 vez
                    2.Tirar 10 veces
                    3.Consultar pity
                    4.Consultar historial
                    5.Salir
                """.trimIndent()
                ,false)
            ui.saltoLinea()

            when (ui.pedirInfo("Elije una opción: ")) {
                "1" ->
                    "2" ->
                "3" ->
                "4" ->
                "5" -> {
                ui.mostrar("Saliendo...",false)
                proceso = false
            }

                else -> println("Opcion no válida")
            }
        }
    }
}