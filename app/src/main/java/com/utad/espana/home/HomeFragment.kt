package com.utad.espana.home

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.leinardi.android.speeddial.SpeedDialActionItem
import com.utad.espana.Comunidad
import com.utad.espana.adapter.ComunidadesAdapter
import com.utad.espana.autonomia.Autonomia
import com.utad.espana.autonomia.ComunidadesProvider
import com.utad.espana.databinding.FragmentHomeBinding
import com.utad.espana.R

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    private lateinit var adaptador: ComunidadesAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //LLAMAMOS LAS FUNCIONES PARA QUE SE CARGUEN AL ABRIR EL FRAGMENT
        cargarListaComunidades()


        binding.fbtnMenu.setOnClickListener{
            menuFlotante()
        }


        return root
    }


    //------------------------------------FUNCIONES-------------------------------------------------

    //FUNCION PARA EL MENU DE LIMPIAR Y ACTUALIZAR
    fun menuFlotante() {
        val popupMenu = PopupMenu(requireContext(), binding.fbtnMenu)
        popupMenu.menuInflater.inflate(R.menu.menu_fab, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_clear -> {
                    parentFragmentManager.popBackStack()

                    true
                }
                R.id.action_update -> {
                    cargarListaComunidades()
                    Toast.makeText(requireContext(), "Se ha actualizado correctamente", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }


    //FUNCION PARA CARGAR LAS COMUNIDADES
    fun cargarListaComunidades() {
        var listaComunidades = ComunidadesProvider.getListaDeComunidades().toMutableList()
        if (::adaptador.isInitialized) {
            adaptador.updateList(listaComunidades)
        } else {
            adaptador = ComunidadesAdapter(listaComunidades) { comunidad ->
                navegarComunidadActivity(comunidad)
            }
            binding.rvComunidades.layoutManager = LinearLayoutManager(requireContext())
            binding.rvComunidades.adapter = adaptador
        }
    }


    //VERIFICACION DE ACCION Y ID PARA ELIMINAR O MODIFICAR
    private val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            val action = data?.getStringExtra("action")
            var id = data?.getIntExtra("id", -1)

            when (action) {
                "eliminar" -> {
                    if (id != null && id != -1) {
                        eliminarComunidad(id)
                    }
                }
                "modificar" -> {
                    val nuevoNombre = data?.getStringExtra("nombre")
                    if (id != null && id != -1 && nuevoNombre != null) {
                        modificarComunidad(id, nuevoNombre)
                    }
                }
            }
        }
    }

    //FUNCION PARA ELIMINAR UNA COMUNIDAD
    fun eliminarComunidad(id: Int) {
        val index = adaptador.listaDeComunidades.indexOfFirst { it.id == id }
        if (index != -1) {
            adaptador.listaDeComunidades.removeAt(index)
            adaptador.notifyItemRemoved(index) // Notifica al adaptador la eliminación
        }
    }


    //FUNCION PARA MODIFICAR EL NOMBRE DE LA COMUNIDAD
    fun modificarComunidad(id: Int, nombreNuevo: String): Unit {

        val index = adaptador.listaDeComunidades.indexOfFirst { it.id == id }
        if(index != -1){
            adaptador.listaDeComunidades[index].nombre = nombreNuevo
            adaptador.notifyItemChanged(index)

        }
    }


    //FUNCION PARA NAVEGAR DEL LISTADO AL ACTIVITY DONDE ELIMINAMOS O MODIFICAMOS EL ITEM SELECCIONADO
    fun navegarComunidadActivity(comunidad: Autonomia){
        val intent = Intent(requireContext(), Comunidad::class.java).apply {
            putExtra("id", comunidad.id)
            putExtra("nombre", comunidad.nombre)
            putExtra("bandera", comunidad.image)
        }
        Toast.makeText(requireContext(), "Soy de la comunidad ${comunidad.nombre}", Toast.LENGTH_SHORT).show()
        startForResult.launch(intent)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
