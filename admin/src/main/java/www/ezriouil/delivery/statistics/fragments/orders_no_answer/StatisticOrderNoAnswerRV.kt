package www.ezriouil.delivery.statistics.fragments.orders_no_answer

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

class StatisticOrderNoAnswerRV(private var myList:List<OrderInfo> , private val allListenerOFStatistics: AllListenerOFStatistics) : RecyclerView.Adapter<StatisticOrderNoAnswerRV.StatisticOrderNoAnswerVH>() {

    class StatisticOrderNoAnswerVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var time_send: AppCompatTextView
        var address: AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView
        var btn_number: AppCompatButton
        var btn_canceled: AppCompatButton
        var btn_reSend: AppCompatButton

        init {
            name = itemView.findViewById(R.id.statistic_no_answer_order_name) as AppCompatTextView
            address = itemView.findViewById(R.id.statistic_no_answer_order_address) as AppCompatTextView
            time_send = itemView.findViewById(R.id.statistic_no_answer_order_timeSend) as AppCompatTextView
            btn_number = itemView.findViewById(R.id.statistic_no_answer_order_btn_number) as AppCompatButton
            price = itemView.findViewById(R.id.statistic_no_answer_order_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.statistic_no_answer_order_quantity) as AppCompatTextView
            btn_canceled = itemView.findViewById(R.id.statistic_no_answer_btn_canceled) as AppCompatButton
            btn_reSend = itemView.findViewById(R.id.statistic_no_answer_btn_resend) as AppCompatButton
        }

    }

    override fun getItemCount() = myList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticOrderNoAnswerVH {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.statistic_orders_no_answer_item,parent,false)
        return StatisticOrderNoAnswerVH(myView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatisticOrderNoAnswerVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.time_send.text = "temps pour renvoyer\n${it.timeSend.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
        holder.btn_canceled.setOnClickListener { allListenerOFStatistics.orderNoAnswerCancel(myList[holder.adapterPosition]) }
        holder.btn_reSend.setOnClickListener { allListenerOFStatistics.orderNoAnswerReSend(myList[holder.adapterPosition]) }
        holder.btn_number.setOnClickListener { allListenerOFStatistics.orderAppel(myList[holder.adapterPosition].phone!!) }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}