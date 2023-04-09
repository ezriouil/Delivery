package www.ezriouil.delivery.statistics.fragments.orders_paid

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.R
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

class StatisticOrderPaidRV (private var myList:List<OrderInfo> , private val history: History) : RecyclerView.Adapter<StatisticOrderPaidRV.StatisticOrderPaidVH>() {

    class StatisticOrderPaidVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var time_confirmed: AppCompatTextView
        var time_paid: AppCompatTextView
        var address: AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView
        var layout: LinearLayoutCompat

        init {
            name = itemView.findViewById(R.id.statistic_order_paid_name) as AppCompatTextView
            address = itemView.findViewById(R.id.statistic_order_paid_address) as AppCompatTextView
            time_confirmed = itemView.findViewById(R.id.statistic_order_paid_time_confirmed) as AppCompatTextView
            time_paid = itemView.findViewById(R.id.statistic_order_paid_time_paid) as AppCompatTextView
            price = itemView.findViewById(R.id.statistic_order_paid_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.statistic_order_paid_quantity) as AppCompatTextView
            layout = itemView.findViewById(R.id.statistic_order_layout) as LinearLayoutCompat
        }
    }

    override fun getItemCount() = myList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticOrderPaidVH {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.statistic_orders_paid_item,parent,false)
        return StatisticOrderPaidVH(myView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatisticOrderPaidVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.time_confirmed.text = "livery\n${it.timeSend.toString()}"
            holder.time_paid.text = "payé\n${it.timePaid.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
        holder.layout.setOnLongClickListener{
            history.addToHistory(myList[holder.adapterPosition])
            return@setOnLongClickListener true
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}
interface History{
    fun addToHistory(orderInfo: OrderInfo)
}