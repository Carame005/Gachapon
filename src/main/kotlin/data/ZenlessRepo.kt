package es.prog2425.prueba.data

import es.prog2425.prueba.model.Genshin
import es.prog2425.prueba.model.Zenless
import java.sql.SQLException
import javax.sql.DataSource

class ZenlessRepo(val dataSource: DataSource) : IZenlessRepo {
    override fun crearTabla() {
        try {
            dataSource.connection.use { con ->
                con.createStatement().use { stm ->
                    val sql = """CREATE TABLE Zenless(
                           id IDENTITY PRIMARY KEY,
                       calidad VARCHAR(255) NOT NULL,
                       nombre VARCHAR(255) NOT NULL)
                   """.trimMargin()
                    stm.executeUpdate(sql)
                }
            }
        }catch (e: IllegalArgumentException){
            throw IllegalArgumentException("ERROR",e)
        }
    }

    override fun insertarValores(calidad : String, nombre : String) {
        dataSource.connection.use { con ->
            val sql = "INSERT INTO Zenless (calidad, nombre) VALUES (?, ?)"
            con.prepareStatement(sql).use { stm ->
                stm.setString(1, calidad)
                stm.setString(2, nombre)
                stm.executeUpdate()
            }
        }
    }

    override fun obtenerTodos(): List<Zenless> {
        val gachapon = mutableListOf<Zenless>()
        try {
            dataSource.connection.use { con ->
                con.createStatement().use { stmt ->
                    val sql = "SELECT * FROM Zenless"
                    stmt.executeQuery(sql).use { rs ->
                        while (rs.next()){
                            val calidad = rs.getString("calidad")
                            val nombre = rs.getString("nombre")
                            gachapon.add(Zenless(calidad, nombre))
                        }
                    }
                }
            }
        }catch (e: SQLException){
            throw IllegalStateException("ERROR ${e.message}")
        }
        return gachapon
    }

    override fun obtenerPity(): Int {
        var entero = 0
        dataSource.connection.use { con ->
            con.createStatement().use { stmt ->
                val sql = "SELECT COUNT(id) FROM Zenless"
                stmt.executeQuery(sql).use { rs ->
                    if (rs.next()) {
                        entero = rs.getInt(1)
                    }
                }
            }
        }
        return entero
    }


    override fun truncarTabla() {
        try {
            dataSource.connection.use { con ->
                con.createStatement().use { stm ->
                    stm.executeUpdate("DELETE FROM Zenless")
                    stm.executeUpdate("ALTER TABLE Zenless ALTER COLUMN id RESTART WITH 1")
                }
            }
        } catch (e: IllegalArgumentException) {
            throw IllegalArgumentException("ERROR", e)
        } catch (e: SQLException) {
            throw SQLException("Error al truncar la tabla: ${e.message}", e)
        }
    }
}