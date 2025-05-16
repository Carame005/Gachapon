package es.prog2425.prueba.data

import es.prog2425.prueba.model.Gachapon
import java.sql.SQLException
import javax.sql.DataSource
import kotlin.use

class GachaponRepo(val dataSource: DataSource) : IGachaponRepo {

    override fun crearTabla() {
       try {
           dataSource.connection.use { con ->
               con.createStatement().use { stm ->
                   val sql = "CREATE TABLE IF NOT EXISTS Gachapon"
                   stm.executeQuery(sql)
               }
           }
       }catch (e: IllegalArgumentException){
           throw IllegalArgumentException("ERROR",e)
       }
    }

    override fun insertarValores() {
        TODO("Not yet implemented")
    }

    override fun obtenerTodos(): List<Gachapon> {
        val gachapon = mutableListOf<Gachapon>()
        try {
            dataSource.connection.use { con ->
                con.createStatement().use { stmt ->
                    val sql = "SELECT * FROM Gachapon"
                    stmt.executeQuery(sql).use { rs ->
                        while (rs.next()){
                            val calidad = rs.getString("calidad")
                            val nombre = rs.getString("nombre")
                            gachapon.add(Gachapon(calidad, nombre))
                        }
                    }
                }
            }
        }catch (e: SQLException){
            throw IllegalStateException("ERROR ${e.message}")
        }
        return gachapon
    }
}