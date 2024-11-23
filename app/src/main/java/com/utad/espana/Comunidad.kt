package com.utad.espana

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.utad.espana.adapter.ComunidadesAdapter
import com.utad.espana.databinding.ActivityComunidadBinding
import com.utad.espana.home.HomeFragment

class Comunidad : AppCompatActivity() {
    private lateinit var binding: ActivityComunidadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityComunidadBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //RECIBIMOS LOS DATOS DEL ITEM SELECCIONADO DESDE EL FRAGMENT
        var itemId = intent.getIntExtra("id", -1)
        var nombre = intent.getStringExtra("nombre").toString()
        var bandera = intent.getIntExtra("bandera", 0)

        //CARGAMOS EL METODO PARA MOSTRAR LAS COMUNIDADES
        mostrarComunidad(itemId,nombre.toString(),bandera)


        //CLICK SOBRE BOTON MODIFICAR PARA CAMBIAR EL NOMRBE
        binding.btnModificar.setOnClickListener {
            val nuevoNombre = binding.et2Nombre.text.toString()
            modificarComunidad( itemId, nuevoNombre)
        }

        //CLICK SOBRE EL BOTON ELIMINAR PARA ELIMINAR LA COMUNIDAD
        binding.btnEliminar.setOnClickListener {
            confirmarEliminacionComunidad(itemId)
        }

    }



    //--------------------------------FUNCIONES ---------------------------------------------


    //FUNCION PARA  ENVIAR LA ACCION ELIMINAR al FRAGMENT
    private fun eliminarComunidad(id: Int) {
        val resultIntent = Intent().apply {
            putExtra("action", "eliminar")
            putExtra("id", id)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }


    //FUNCION PARA ENVIAR LA ACCION DE MODIFICAR AL FRAGMENT
    fun modificarComunidad(itemId:Int, nombre :String) {
        val resultIntent = Intent().apply {
            putExtra("action", "modificar")
            putExtra("id", itemId)
            putExtra("nombre", nombre)
        }
        setResult(RESULT_OK, resultIntent)
        finish()
    }


    //MOSTRAMOS LOS DATOS RECIBIDOS DE LA COMUNIDAD DESDE  FRAGMENT A LA VISTA
    fun mostrarComunidad(id: Int, nombre: String,bandera: Int){
        binding.et2Nombre.setText(nombre)
        binding.iv2Bandera.setImageResource(bandera)
    }


    //VENTANA EMERGENTE PARA CONFIRMAR LA ELIMINACION O CANCELACION DE LA COMUNIDAD
    fun confirmarEliminacionComunidad(id: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirmar eliminación")
        builder.setMessage("¿Estás seguro de que deseas eliminar esta comunidad?")

        builder.setPositiveButton("Eliminar") { dialog, _ ->
            eliminarComunidad(id)
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }
}