package com.example.spendwisely.fragmentos.cuentas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.spendwisely.R
import com.example.spendwisely.data.entidades.Cuenta

class CuentaListAdapter(private var mListener : OnCuentaClickListener) : RecyclerView.Adapter<CuentaListAdapter.CuentaViewHolder>() {

    private var cuentaList = emptyList<Cuenta>()

    inner class CuentaViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        fun setListeners(cuenta: Cuenta) {
            itemView.setOnClickListener {
                mListener.onItemClick(cuenta)
            }
            itemView.setOnLongClickListener {
                mListener.onLongItemClick(cuenta)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CuentaViewHolder {
        return CuentaViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_cuenta_rv, parent, false))
    }

    override fun onBindViewHolder(holder: CuentaViewHolder, position: Int) {
        val currentItem = cuentaList[position]
        holder.setListeners(currentItem)
        holder.itemView.findViewById<TextView>(R.id.tv_nombre).text = currentItem.nombre
        holder.itemView.findViewById<TextView>(R.id.tv_saldo).text = currentItem.saldo.toString()
    }

    override fun getItemCount(): Int {
        return cuentaList.size
    }

    fun setData(cuenta: List<Cuenta>) {
        this.cuentaList = cuenta
        notifyDataSetChanged()
    }

}