package com.wsbinz.server.routes

import com.wsbinz.dao.DAOFacade
import com.wsbinz.model.University
import com.wsbinz.server.CitiesPage
import io.ktor.application.call
import io.ktor.freemarker.FreeMarkerContent
import io.ktor.locations.get
import io.ktor.response.respond
import io.ktor.routing.Route

fun Route.citiesPage(dao: DAOFacade) {
    get<CitiesPage> {
        val universities = dao.getAllUniversities()
        val poznan = mutableListOf<University>()
        val wroclaw = mutableListOf<University>()
        val warszawa = mutableListOf<University>()
        universities.map {
            if (it.city == "Poznań") poznan.add(it)
            if (it.city == "Wrocław") wroclaw.add(it)
            if (it.city == "Warszawa") warszawa.add(it)
        }

        poznan.sortBy { it.name }
        warszawa.sortBy { it.name }
        wroclaw.sortBy { it.name }

        call.respond(
            FreeMarkerContent(
                "cities.ftl",
                mapOf(
                    "poznan" to poznan,
                    "wroclaw" to wroclaw,
                    "warszawa" to warszawa
                )
            )
        )
    }
}