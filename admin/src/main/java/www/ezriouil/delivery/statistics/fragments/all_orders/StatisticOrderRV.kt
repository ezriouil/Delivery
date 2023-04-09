package www.ezriouil.delivery.statistics.fragments.all_orders

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.R
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

class StatisticOrderRV(private var myList:List<OrderInfo>) : RecyclerView.Adapter<StatisticOrderRV.StatisticOrderVH>() {

    class StatisticOrderVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var timeSend: AppCompatTextView
        var status: AppCompatTextView
        var address: AppCompatTextView
        val timeRespond : AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView
        var layout:LinearLayoutCompat

        init {
            name = itemView.findViewById(R.id.statistic_order_name) as AppCompatTextView
            timeSend = itemView.findViewById(R.id.statistic_order_time_send) as AppCompatTextView
            timeRespond = itemView.findViewById(R.id.statistic_order_time_response) as AppCompatTextView
            status = itemView.findViewById(R.id.statistic_order_status) as AppCompatTextView
            address = itemView.findViewById(R.id.statistic_order_address) as AppCompatTextView
            price = itemView.findViewById(R.id.statistic_order_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.statistic_order_quantity) as AppCompatTextView
            layout = itemView.findViewById(R.id.statistic_order_layout) as LinearLayoutCompat
        }

    }
    override fun getItemCount() = myList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=
        StatisticOrderVH(LayoutInflater.from(parent.context).inflate(R.layout.statistic_orders_item,parent,false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatisticOrderVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.timeSend.text = "envoyer\n${it.timeSend.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.timeRespond.text = "réponse\n${it.timePaid.toString()}"
            holder.status.text = it.status.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
        when(holder.status.text){
             Constants.ORDER_STATUS_CONFIRMED -> {holder.layout.setBackgroundColor(Color.GREEN) }
            Constants.ORDER_STATUS_ANNULER -> {holder.layout.setBackgroundColor(Color.RED) }
            Constants.ORDER_STATUS_WAITING -> {holder.layout.setBackgroundColor(Color.YELLOW) }
            else ->{holder.layout.setBackgroundColor(Color.WHITE)}
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}