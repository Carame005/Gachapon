package es.prog2425.prueba.ui

interface IEntradaSalida {
    fun mostrar(texto: String, saltoLinea: Boolean)
    fun saltoLinea()
    fun pedirInfo(msj : String): String
    fun pedirEntero(msj : String): Int
    fun limpiarPantalla()
    fun pausar()
}