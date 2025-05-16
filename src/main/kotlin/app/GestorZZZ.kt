package es.prog2425.prueba.app

import es.prog2425.prueba.services.IZenlessService
import es.prog2425.prueba.ui.IEntradaSalida
import java.sql.SQLException

class GestorZZZ(private val ui : IEntradaSalida, private val zenlessService : IZenlessService) {

    var pity4 = 0
    var pity5 = 0

    fun cargarPityDesdeBD() {
        ejecutarOperacion {
            val items = zenlessService.obtenerTodos()
            val reversedItems = items.asReversed()

            pity5 = reversedItems.indexOfFirst { it.calidad == "S" }
            if (pity5 == -1) pity5 = items.size

            pity4 = reversedItems.indexOfFirst { it.calidad == "A" }
            if (pity4 == -1) pity4 = items.size
        }
    }

    val objetos3Estrellas = listOf("Amplificador Aturdidor","Amplificador Defensor","Amplificador Apoyo","Amplificador Anomalía","Amplificador Atacante")
    val objetos4Estrellas = listOf("Amplificador Aturdidor","Amplificador Defensor","Amplificador Apoyo","Amplificador Anomalía","Amplificador Atacante","Lucy","Soukaku","Piper","Pulchra","Anby","Corin","Nicole","Anton","Billy","Seth","Ben")
    val objetos5Estrellas = listOf("Amplificador Aturdidor","Amplificador Defensor","Amplificador Apoyo","Amplificador Anomalía","Amplificador Atacante","Harumasa","Lighter","Soldier 11","Zhu Yuan","Yanagi","Hugo","Trigger","Evelyn","Nekomata","Miyabi","Caesar","Koleda","Burnice","Lycaon","Grace","Rina","Ellen Joe","Astra Yao","Qingyi","Jane Doe","Vivian","Anby Silver Soldier")

    fun menuZZZ(){
        ui.limpiarPantalla()
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
                "1" -> tirarUnaVez()
                "2" -> tirarDiezVeces()
                "3" -> mostrarPity()
                "4" -> mostrarHistorial()
                "5" -> {
                ui.mostrar("Saliendo...",false)
                proceso = false
            }

                else -> println("Opcion no válida")
            }
        }
    }

    fun tirarUnaVez(): String {
        pity4++
        pity5++

        val calidad = when {
            pity5 >= 90 -> "S"
            pity4 >= 10 -> "A"
            else -> {
                val prob = (1..1000).random()
                when {
                    prob <= 6 -> "S"
                    prob <= 57 -> "A"
                    else -> "B"
                }
            }
        }

        val objeto = when (calidad) {
            "B" -> objetos3Estrellas.random()
            "A" -> objetos4Estrellas.random()
            else -> objetos5Estrellas.random()
        }

        agregarGachapon(calidad, objeto)

        if (calidad == "S") {
            pity5 = 0
            pity4 = 0
            ejecutarOperacion {
                zenlessService.truncarTabla()
            }
        } else if (calidad == "A") {
            pity4 = 0
        }

        val resultado = "$calidad $objeto"
        ui.mostrar(resultado, true)
        ui.pausar()
        ui.limpiarPantalla()
        return resultado
    }



    fun tirarDiezVeces() {
        repeat(10) {
            tirarUnaVez()
        }
    }

    fun mostrarPity() {
        ui.mostrar("Tiradas desde el último A: $pity4", false)
        ui.saltoLinea()
        ui.mostrar("Tiradas desde el último S: $pity5", false)
        ui.pausar()
        ui.limpiarPantalla()
    }



    private fun ejecutarOperacion(bloque: () -> Unit) {
        try {
            bloque()
        } catch (e: IllegalArgumentException) {
            ui.mostrarError("Argumentos no válidos: ${e.message}",true)
        } catch (e: SQLException) {
            ui.mostrarError("Problemas con la BDD: ${e.message}",true)
        } catch (e: Exception) {
            ui.mostrarError("Se produjo un error: ${e.message}",true)
        }
    }

    private fun agregarGachapon(calidad : String,nombre : String) {
        ejecutarOperacion {
            zenlessService.insertarValores(calidad, nombre )
        }
    }

    private fun mostrarHistorial() {
        ejecutarOperacion {
            val users = zenlessService.obtenerTodos()
            if (users.isEmpty()) {
                ui.mostrar("No hay historial.",false)
            } else {
                users.forEach { ui.mostrar("ID: ${it.calidad} - Nombre: ${it.nombre}",true) }
                ui.pausar()
            }
            ui.pausar()
            ui.limpiarPantalla()
        }
    }
}