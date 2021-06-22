package com.sephora.minhaagenda

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.sephora.minhaagenda.classes.Contato


class ContatosAdapter (private  val dataSet : MutableList<Contato>, private val listener : OnItemClickListener) :
    RecyclerView.Adapter<ContatosAdapter.ContatosViewHolder>(){


    inner class ContatosViewHolder(view : View) : RecyclerView.ViewHolder(view),  View.OnClickListener{
        val name = view.findViewById<TextView>(R.id.itemName)
        val phone = view.findViewById<TextView>(R.id.itemTelephone)
        val info = view.findViewById<TextView>(R.id.itemInformation)
        val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)

        init {
            btnDelete.setOnClickListener (this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onBtnDeleteClick(position)
            }
        }

    }

    interface OnItemClickListener {
        fun onBtnDeleteClick (position: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContatosViewHolder {
        val instanceView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_model, parent, false)
        return ContatosViewHolder(instanceView)
    }

    override fun onBindViewHolder(holder: ContatosViewHolder, position: Int) {
        holder.name.text = dataSet[position].nome
        holder.phone.text = dataSet[position].telefone
        holder.info.text = dataSet[position].descricao
        holder.btnDelete

    }

    override fun getItemCount(): Int = dataSet.size

}