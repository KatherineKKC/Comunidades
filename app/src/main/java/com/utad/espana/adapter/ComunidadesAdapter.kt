package com.utad.espana.adapter
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.utad.espana.ComunidadesDiffCallback
import com.utad.espana.adapter.ComunidadesAdapter.ComunidadHolder
import com.utad.espana.autonomia.Autonomia
import com.utad.espana.databinding.ItemsComunidadesBinding
import com.utad.espana.R

class ComunidadesAdapter( var listaDeComunidades: MutableList<Autonomia>,private val onClickListener: (Autonomia)-> Unit): RecyclerView.Adapter<ComunidadHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComunidadHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemsComunidadesBinding.inflate(inflater, parent, false)
        return ComunidadHolder(binding)
    }


    override fun onBindViewHolder(holder: ComunidadHolder, position: Int) {
        val comunidad = listaDeComunidades[position]
        holder.binding.tvNombre.text = comunidad.nombre
        Glide.with(holder.itemView.context)
            .load(comunidad.image)
            .into(holder.binding.ivBandera)

        holder.itemView.setOnClickListener {
            onClickListener(comunidad)
        }
    }


    override fun getItemCount(): Int {
        return listaDeComunidades.size
    }

    //CLASE COMUNIDADHOLDER
    inner class ComunidadHolder(val binding: ItemsComunidadesBinding): RecyclerView.ViewHolder(binding.root)


    // -------------------------------FUNCIONES -------------------------------------------------
    //FUNCION PARA ACTUALIZAR LA LISTA Y NOTIFICAR LOS CAMBIOS CON DIFFUTIL SE REALIZA LA COMPARACION DE LOS CAMBIOS AUTO
    fun updateList(newList: List<Autonomia>) {
        val diffCallback = ComunidadesDiffCallback(listaDeComunidades, newList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listaDeComunidades.clear()
        listaDeComunidades.addAll(newList)
        diffResult.dispatchUpdatesTo(this)
    }
}