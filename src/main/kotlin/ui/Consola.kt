package es.prog2425.prueba.ui

class Consola : IEntradaSalida{

    override fun mostrar(texto: String, saltoLinea: Boolean) {
        print("$texto${if (saltoLinea) "\n" else ""}")
    }
    override fun saltoLinea() {
        mostrar("",true)
    }

    override fun pedirInfo(msj : String) : String {
        print(msj)
        return readln()
    }

    override fun pedirEntero(msj : String) : Int {
        return pedirInfo(msj).toInt()
    }

    override fun limpiarPantalla() {
        repeat(20) { print("") }
    }

    override fun pausar() {
        readln()
    }

    override fun mostrarError(msj: String, salto: Boolean) {
        mostrar(msj, salto)
    }
}