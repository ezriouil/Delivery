package www.ezriouil.delivery.livrey

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.AllListener
import www.ezriouil.delivery.OrderInfo
import www.ezriouil.delivery.R

class LiveryOrdersRV(private var myList:List<OrderInfo>, val allListener: AllListener) : RecyclerView.Adapter<LiveryOrdersRV.LiveryOrdersVH>() {
    class LiveryOrdersVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var timeSend: AppCompatTextView
        val timeLivery : AppCompatTextView
        var address: AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView
        var btn_paid :AppCompatButton

        init {
            name = itemView.findViewById(R.id.livery_order_name) as AppCompatTextView
            timeSend = itemView.findViewById(R.id.livery_order_time_send) as AppCompatTextView
            timeLivery = itemView.findViewById(R.id.livery_order_time_livery) as AppCompatTextView
            address = itemView.findViewById(R.id.livery_order_address) as AppCompatTextView
            price = itemView.findViewById(R.id.livery_order_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.livery_order_quantity) as AppCompatTextView
            btn_paid = itemView.findViewById(R.id.livery_order_btn_paid) as AppCompatButton
        }

    }
    override fun getItemCount() = myList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= LiveryOrdersVH(LayoutInflater.from(parent.context).inflate(R.layout.livery_orders_item,parent,false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LiveryOrdersVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.timeSend.text = "envoyer\n${it.timeSend.toString()}"
            holder.timeLivery.text = "livery\n${it.timePaid.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
        holder.btn_paid.setOnClickListener { allListener.orderLiveryBtnPaid(myList[holder.adapterPosition]) }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}