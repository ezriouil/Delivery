package www.ezriouil.delivery.paid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.OrderInfo
import www.ezriouil.delivery.R

class PaidOrdersRV(private var myList:List<OrderInfo>) : RecyclerView.Adapter<PaidOrdersRV.PaidOrdersVH>() {
    class PaidOrdersVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var timeSend: AppCompatTextView
        var address: AppCompatTextView
        val timeRespond : AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView

        init {
            name = itemView.findViewById(R.id.paid_order_name) as AppCompatTextView
            timeSend = itemView.findViewById(R.id.paid_order_time_livery) as AppCompatTextView
            timeRespond = itemView.findViewById(R.id.paid_order_time_paid) as AppCompatTextView
            address = itemView.findViewById(R.id.paid_order_address) as AppCompatTextView
            price = itemView.findViewById(R.id.paid_order_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.paid_order_quantity) as AppCompatTextView
        }

    }
    override fun getItemCount() = myList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= PaidOrdersVH(LayoutInflater.from(parent.context).inflate(R.layout.paid_orders_item,parent,false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: PaidOrdersVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.timeSend.text = "étè payée\n${it.isPaid}"
            holder.timeRespond.text = "payée\n${it.timePaid.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}