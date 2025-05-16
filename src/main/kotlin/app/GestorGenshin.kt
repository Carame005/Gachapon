package es.prog2425.prueba.app

import es.prog2425.prueba.services.IGenshinService
import es.prog2425.prueba.ui.IEntradaSalida
import java.sql.SQLException

class GestorGenshin(private val ui : IEntradaSalida,private val genshinServicio : IGenshinService) {
    var pity4 = 0
    var pity5 = 0

    fun cargarPityDesdeBD() {
        ejecutarOperacion {
            val items = genshinServicio.obtenerTodos()
            val reversedItems = items.asReversed()

            pity5 = reversedItems.indexOfFirst { it.calidad == "5⭐" }
            if (pity5 == -1) pity5 = items.size

            pity4 = reversedItems.indexOfFirst { it.calidad == "4⭐" }
            if (pity4 == -1) pity4 = items.size
        }
    }



    val objetos3Estrellas = listOf("Lanza","Espada","Mandoble","Arco","Catalizador")

    val objetos4Estrellas = listOf("Lanza","Espada","Mandoble","Arco","Catalizador","Amber", "Bárbara", "Beidou", "Bennett", "Candace", "Charlotte", "Chevreuse", "Chongyun",
        "Collei", "Diona", "Dori", "Faruzán", "Fischl", "Gaming", "Gorou", "Kaeya", "Kirara",
        "Kuki Shinobu", "Kujou Sara", "Lan Yan", "Layla", "Lisa", "Mika", "Ningguang", "Noelle",
        "Ororon", "Rosaria", "Sayu", "Shikanoin Heizou", "Sucrose", "Thoma", "Xiangling",
        "Xingqiu", "Xinyan", "Yanfei", "Yaoyao", "Yun Jin","Ifa")

    val objetos5Estrellas = listOf("Lanza","Espada","Mandoble","Arco","Catalizador","Albedo", "Alhaitham", "Arlecchino", "Baizhu", "Chiori", "Clorinde", "Cyno", "Dehya",
    "Diluc", "Eula", "Escoffier", "Furina", "Ganyu", "Hu Tao", "Itto", "Jean", "Kaedehara Kazuha",
    "Kamisato Ayaka", "Kamisato Ayato", "Keching", "Klee", "Lyney", "Mona", "Nahida", "Navia",
    "Neuvillette", "Nilou", "Qiqi", "Raiden Shogun", "Shenhe", "Sigewinne", "Tartaglia",
    "Tighnari", "Venti", "Wanderer", "Wriothesley", "Xiao", "Yae Miko", "Yelan", "Yoimiya",
    "Zhongli")


    fun menuGenshin(){
        ui.limpiarPantalla()
        var proceso = true
        cargarPityDesdeBD()
        while (proceso) {
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
                    ui.mostrar("Saliendo...",true)
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
                genshinServicio.truncarTabla()
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
        ui.mostrar("Tiradas desde el último 4⭐: $pity4", true)
        ui.saltoLinea()
        ui.mostrar("Tiradas desde el último 5⭐: $pity5", true)
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
            genshinServicio.insertarValores(calidad, nombre )
        }
    }

    private fun mostrarHistorial() {
        ejecutarOperacion {
            val users = genshinServicio.obtenerTodos()
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