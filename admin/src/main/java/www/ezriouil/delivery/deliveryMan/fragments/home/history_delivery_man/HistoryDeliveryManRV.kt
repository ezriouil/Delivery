package www.ezriouil.delivery.deliveryMan.fragments.home.history_delivery_man

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.R

class HistoryDeliveryManRV (private var myList:List<OrderInfoHistory>) : RecyclerView.Adapter<HistoryDeliveryManRV.HistoryDeliveryManVH>() {

    class HistoryDeliveryManVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var time_paid: AppCompatTextView
        var address: AppCompatTextView
        var price: AppCompatTextView

        init {
            name = itemView.findViewById(R.id.history_order_name) as AppCompatTextView
            address = itemView.findViewById(R.id.history_order_address) as AppCompatTextView
            time_paid = itemView.findViewById(R.id.history_order_timePaid) as AppCompatTextView
            price = itemView.findViewById(R.id.history_order_price) as AppCompatTextView
        }
    }

    override fun getItemCount() = myList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryDeliveryManVH {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.delivery_man_history_item_rv,parent,false)
        return HistoryDeliveryManVH(myView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: HistoryDeliveryManVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = "commande\n${it.name.toString().uppercase()}"
            holder.time_paid.text = "temps de payé\n${it.timePaid.toString()}"
            holder.address.text = "l'adress\n${it.address.toString().uppercase()}"
            holder.price.text = "prix\n${it.price} DH"
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfoHistory>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}