package www.ezriouil.delivery.statistics.fragments.orders_delivery_done

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.R
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

class StatisticOrderLiveryDoneRV (private var myList:List<OrderInfo>) : RecyclerView.Adapter<StatisticOrderLiveryDoneRV.StatisticOrderLiveryDoneVH>() {

    class StatisticOrderLiveryDoneVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var time_delivery: AppCompatTextView
        var address: AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView

        init {
            name = itemView.findViewById(R.id.statistic_done_order_name) as AppCompatTextView
            address = itemView.findViewById(R.id.statistic_done_order_address) as AppCompatTextView
            time_delivery = itemView.findViewById(R.id.statistic_done_order_time_delivery) as AppCompatTextView
            price = itemView.findViewById(R.id.statistic_done_order_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.statistic_done_order_quantity) as AppCompatTextView
        }
    }

    override fun getItemCount() = myList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticOrderLiveryDoneVH {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.statistic_orders_delivery_done_item,parent,false)
        return StatisticOrderLiveryDoneVH(myView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatisticOrderLiveryDoneVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.time_delivery.text = "temps de livraison\n${it.timeSend.toString()}"
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