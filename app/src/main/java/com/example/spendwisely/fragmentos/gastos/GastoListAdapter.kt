package com.example.spendwisely.fragmentos.gastos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Gasto

class GastoListAdapter(private var mListener : OnItemClickListener) : RecyclerView.Adapter<GastoListAdapter.GastoViewHolder>() {

    private var gastoList = emptyList<Gasto>()

    inner class GastoViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun setListeners(gasto: Gasto) {
            itemView.setOnClickListener {
                mListener.onItemClick(gasto)
            }
            itemView.setOnLongClickListener {
                mListener.onLongItemClick(gasto)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GastoViewHolder {
        return GastoViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_gasto_rv, parent, false))
    }

    override fun onBindViewHolder(holder: GastoViewHolder, position: Int) {
        val currentItem = gastoList[position]
        holder.setListeners(currentItem)
        holder.itemView.findViewById<TextView>(R.id.tv_cantidad).text = currentItem.cantidad.toString()
        holder.itemView.findViewById<TextView>(R.id.tv_nota).text = currentItem.nota
    }

    override fun getItemCount(): Int {
        return gastoList.size
    }

    fun setData(gasto : List<Gasto>) {
        this.gastoList = gasto
        notifyDataSetChanged()
    }

}