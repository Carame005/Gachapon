package es.prog2425.prueba.app

import es.prog2425.prueba.services.IStarRailService
import es.prog2425.prueba.ui.IEntradaSalida
import java.sql.SQLException
import kotlin.collections.listOf

class GestorHSR(private val ui : IEntradaSalida, private val starRailService: IStarRailService) {

    var pity4 = 0
    var pity5 = 0

    fun cargarPityDesdeBD() {
        ejecutarOperacion {
            val items = starRailService.obtenerTodos()
            val reversedItems = items.asReversed()

            pity5 = reversedItems.indexOfFirst { it.calidad == "5⭐" }
            if (pity5 == -1) pity5 = items.size

            pity4 = reversedItems.indexOfFirst { it.calidad == "4⭐" }
            if (pity4 == -1) pity4 = items.size
        }
    }

    val objetos3Estrellas = listOf("Cono de luz Destruccion","Cono de luz Erudición","Cono de luz Armonía","Cono de luz Nihilidad","Cono de luz Conservación","Cono de luz Abundancia","Cono de luz Reminiscencia","Cono de luz Cacería")

    val objetos4Estrellas = listOf(
        "Cono de luz Destruccion","Cono de luz Erudición","Cono de luz Armonía","Cono de luz Nihilidad","Cono de luz Conservación","Cono de luz Abundancia","Cono de luz Reminiscencia","Cono de luz Cacería","Siete de marzo","Dan Heng","Arlan","Asta","Herta","Serval","Natasha"
        ,"Pela","Sampo","Hook","Lynx","Luka","Qingque","Tingyun","Sushang",
        "Yukong","Guinaifen","Xueyi","Hanya","Moze","Gahlager","Misha")

    val objetos5Estrellas = listOf("Cono de luz Destruccion","Cono de luz Erudición","Cono de luz Armonía","Cono de luz Nihilidad","Cono de luz Conservación","Cono de luz Abundancia","Cono de luz Reminiscencia","Cono de luz Cacería","Himeko","Welt","Kafka","Silver Wolf","Bronya","Seele","Gepard","Clara",
        "Topaz","Luocha","Ying Juan","Fu Xuan","Yanqing","Jingliu","Dan Heng Imbibitor Lunae","Huohuo",
        "Yunli","Linghsa","Fugue","Ruan Mei","Aventurine","Dr Ratio","Sparkle",
        "Black Swan","Acheron","Robin","Firefly","The Herta","Tribbie","Castorice","Blade"
        ,"Bailu","Jiaoqiu","Feixiao","Argenti","Sunday","Jade","Boothill",
        "Rappa","Aglaea","Mydei","Anaxa")


    fun menuHSR(){
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
            pity5 >= 90 -> "5⭐"
            pity4 >= 10 -> "4⭐"
            else -> {
                val prob = (1..1000).random()
                when {
                    prob <= 6 -> "5⭐"
                    prob <= 57 -> "4⭐"
                    else -> "3⭐"
                }
            }
        }

        val objeto = when (calidad) {
            "3⭐" -> objetos3Estrellas.random()
            "4⭐" -> objetos4Estrellas.random()
            else -> objetos5Estrellas.random()
        }

        agregarGachapon(calidad, objeto)

        if (calidad == "5⭐") {
            pity5 = 0
            pity4 = 0
            ejecutarOperacion {
                starRailService.truncarTabla()
            }
        } else if (calidad == "4⭐") {
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
        ui.mostrar("Tiradas desde el último 4⭐: $pity4", false)
        ui.saltoLinea()
        ui.mostrar("Tiradas desde el último 5⭐: $pity5", false)
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
            starRailService.insertarValores(calidad, nombre )
        }
    }

    private fun mostrarHistorial() {
        ejecutarOperacion {
            val users = starRailService.obtenerTodos()
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