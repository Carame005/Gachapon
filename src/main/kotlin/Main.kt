package es.prog2425.prueba

import es.prog2425.prueba.app.GachaponManager
import es.prog2425.prueba.app.GestorGenshin
import es.prog2425.prueba.app.GestorHSR
import es.prog2425.prueba.app.GestorWuwa
import es.prog2425.prueba.app.GestorZZZ
import es.prog2425.prueba.data.GenshinRepo
import es.prog2425.prueba.data.StarRailRepo
import es.prog2425.prueba.data.WuwaRepo
import es.prog2425.prueba.data.ZenlessRepo
import es.prog2425.prueba.data.db.DataSourceFactory
import es.prog2425.prueba.services.GenshinService
import es.prog2425.prueba.services.StarRailService
import es.prog2425.prueba.services.WuwaService
import es.prog2425.prueba.services.ZenlessService
import es.prog2425.prueba.ui.Consola

fun main() {
    val datasource = DataSourceFactory.getDataSource()
    GachaponManager(Consola(),
        GestorHSR(Consola(), StarRailService(StarRailRepo(datasource)) ),
        GestorGenshin(Consola(),GenshinService(GenshinRepo(datasource))),
        GestorZZZ(Consola(), ZenlessService(ZenlessRepo(datasource))),
        GestorWuwa(Consola(), WuwaService(WuwaRepo(datasource)))

    ).mostrarMenu()
}