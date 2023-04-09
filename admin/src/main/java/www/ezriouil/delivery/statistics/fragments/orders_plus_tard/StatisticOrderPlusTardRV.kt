package www.ezriouil.delivery.statistics.fragments.orders_plus_tard

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.R
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo
import www.ezriouil.delivery.statistics.AllListenerOFStatistics
import java.text.SimpleDateFormat
import java.time.LocalDate

class StatisticOrderPlusTardRV (private var myList:List<OrderInfo>, private val allListenerOFStatistics: AllListenerOFStatistics) : RecyclerView.Adapter<StatisticOrderPlusTardRV.StatisticOrderPlusTardVH>() {
    class StatisticOrderPlusTardVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var time_send: AppCompatTextView
        var address: AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView
        var btn_reSend: AppCompatButton


        init {
            name = itemView.findViewById(R.id.statistic_plus_tard_order_name) as AppCompatTextView
            address = itemView.findViewById(R.id.statistic_plus_tard_order_address) as AppCompatTextView
            time_send = itemView.findViewById(R.id.statistic_plus_tard_order_timeSend) as AppCompatTextView
            price = itemView.findViewById(R.id.statistic_plus_tard_order_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.statistic_plus_tard_order_quantity) as AppCompatTextView
            btn_reSend = itemView.findViewById(R.id.statistic_plus_tard_btn_resend) as AppCompatButton
        }
    }
    override fun getItemCount() = myList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticOrderPlusTardVH {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.statistic_orders_plus_tard_item,parent,false)
        return StatisticOrderPlusTardVH(myView)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: StatisticOrderPlusTardVH, position: Int){
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.time_send.text = "temps pour renvoyer\n${it.timePaid.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
        val myDate = "${LocalDate.now().year}/${myList[holder.adapterPosition].timePaid}:00"
        val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = sdf.parse(myDate)
        if (date != null) {
            if (date.time<System.currentTimeMillis()){
                holder.btn_reSend.visibility = View.VISIBLE
            } else holder.btn_reSend.visibility = View.GONE
        }
        holder.btn_reSend.setOnClickListener { allListenerOFStatistics.orderPlusTardReSend(myList[holder.adapterPosition]) }
    }
}