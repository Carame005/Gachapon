package es.prog2425.prueba.data

import es.prog2425.prueba.model.Gachapon
import java.io.StringReader
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

    override fun insertarValores(calidad : String, nombre : String) {
        dataSource.connection.use { con ->
            val sql = "INSERT INTO Gachapon (calidad, nombre) VALUES (?, ?)"
            con.prepareStatement(sql).use { stm ->
                stm.setString(1, calidad)
                stm.setString(2, nombre)
                stm.executeUpdate()
            }
        }
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