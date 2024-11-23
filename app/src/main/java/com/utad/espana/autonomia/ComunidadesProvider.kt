package com.utad.espana.autonomia

import com.utad.espana.R

class ComunidadesProvider {

    companion object {

        val comunidadesIniciales = mutableListOf<Autonomia>(
            Autonomia(1, "Andalucia", R.drawable.andalucia),
            Autonomia(2, "Aragón", R.drawable.aragon),
            Autonomia(3, "Asturias", R.drawable.asturias),
            Autonomia(4, "Baleares", R.drawable.baleares),
            Autonomia(5, "Canarias", R.drawable.canarias),
            Autonomia(6, "Cantabria", R.drawable.cantabria),
            Autonomia(7, "Castilla y León", R.drawable.castillaleon),
            Autonomia(8, "Castilla-La Mancha", R.drawable.castillamancha),
            Autonomia(9, "Cataluña", R.drawable.catalunya),
            Autonomia(10, "Ceuta", R.drawable.ceuta),
            Autonomia(11, "Extremadura", R.drawable.extremadura),
            Autonomia(12, "Galicia", R.drawable.galicia),
            Autonomia(13, "La Rioja", R.drawable.larioja),
            Autonomia(14, "Madrid", R.drawable.madrid),
            Autonomia(15, "Melilla", R.drawable.melilla),
            Autonomia(16, "Murcia", R.drawable.murcia),
            Autonomia(17, "Navarra", R.drawable.navarra),
            Autonomia(18, "País Vasco", R.drawable.paisvasco),
            Autonomia(19, "España", R.drawable.spain),
            Autonomia(20, "Valencia", R.drawable.valencia)


        )

        fun getListaDeComunidades(): MutableList<Autonomia> {
            return comunidadesIniciales.toMutableList()
        }
    }

}