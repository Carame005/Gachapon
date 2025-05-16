package es.prog2425.prueba.data.db

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import es.prog2425.prueba.data.db.Mode
import org.h2.jdbcx.JdbcDataSource
import javax.sql.DataSource

object DataSourceFactory {

    private const val JDBCURL = "jdbc:h2:file:./data/historial"  // URL para la base de datos H2 en archivo
    private const val USER = "sa"  // Nombre de usuario para la base de datos
    private const val PASSWORD = ""  // Contraseña para la base de datos (vacía en este caso)


    fun getDataSource(mode: Mode = Mode.HIKARI): DataSource {
        return when (mode) {
            Mode.HIKARI -> {
                val config = HikariConfig().apply {
                    jdbcUrl = JDBCURL
                    username = USER
                    password = PASSWORD
                    driverClassName = "org.h2.Driver"
                    maximumPoolSize = 10
                }
                HikariDataSource(config)
            }
            Mode.SIMPLE -> {
                JdbcDataSource().apply {
                    setURL(JDBCURL)
                    user = USER
                    password = PASSWORD
                }
            }
        }
    }
}