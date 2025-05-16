package es.prog2425.prueba.app

import es.prog2425.prueba.ui.IEntradaSalida

class GachaponManager(
    private val ui : IEntradaSalida,
    private val gestorHSR: GestorHSR,
    private val gestorGenshin: GestorGenshin,
    private val gestorZZZ: GestorZZZ,
    private val gestorWuwa: GestorWuwa
) {

    private var running = true
    fun mostrarMenu() {
        while (running) {
            ui.limpiarPantalla()
            ui.mostrar(
                """
                   ---Gachapon---
                   1.Genshin Impact
                   2.Honkai Star Rail
                   3.Zenless Zone Zero
                   4.Wuthering Waves
                   5.Salir
               """.trimIndent()
            ,true)

            ui.saltoLinea()

            when (ui.pedirInfo("Elije una opción: ")) {
                "1" -> gestorGenshin.menuGenshin()
                "2" -> gestorHSR.menuHSR()
                "3" -> gestorZZZ.menuZZZ()
                "4" -> gestorWuwa.menuWuwa()
                "5" -> salir()
                else -> println("Opcion no válida")
            }
            ui.pausar()
        }
    }

    private fun salir() {
        ui.mostrar("Saliendo...",true)
        running = false
    }
}