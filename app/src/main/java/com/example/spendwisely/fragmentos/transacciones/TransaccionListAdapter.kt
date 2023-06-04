package com.example.spendwisely.fragmentos.transacciones

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Transaccion
import com.example.spendwisely.otros.Categorias
import com.google.android.material.card.MaterialCardView
import java.util.Locale

class TransaccionListAdapter(private var mListener : OnTransaccionClickListener) : RecyclerView.Adapter<TransaccionListAdapter.TransaccionViewHolder>() {

    private var transaccionList = emptyList<Transaccion>()

    inner class TransaccionViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setListeners(transaccion: Transaccion) {
            itemView.setOnClickListener {
                mListener.onItemClick(transaccion)
            }
            itemView.setOnLongClickListener {
                mListener.onLongItemClick(transaccion)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransaccionViewHolder {
        return TransaccionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_transaccion_rv, parent, false))
    }

    override fun onBindViewHolder(holder: TransaccionViewHolder, position: Int) {
        val currentItem = transaccionList[position]
        holder.setListeners(currentItem)
        val dinero = (if (currentItem.esGasto) "-" else "+") + String.format(Locale.getDefault(),"%.2f",currentItem.cantidad)+"€"
        holder.itemView.findViewById<TextView>(R.id.tv_cantidad).text = dinero
        holder.itemView.findViewById<TextView>(R.id.tv_cantidad).setTextColor(if (currentItem.esGasto) Color.RED else Color.parseColor("#FF006001"))
        holder.itemView.findViewById<TextView>(R.id.tv_nota).text = currentItem.nota
        holder.itemView.findViewById<ImageView>(R.id.categoria_icon).setImageResource(Categorias.obtenerImagenCategoria(currentItem.categoria))
        holder.itemView.findViewById<MaterialCardView>(R.id.rv_item_transaccion).strokeColor = Categorias.obtenerColorCategoria(currentItem.categoria)
    }

    override fun getItemCount(): Int {
        return transaccionList.size
    }

    fun setData(transacciones : List<Transaccion>) {
        this.transaccionList = transacciones
        notifyDataSetChanged()
    }

}