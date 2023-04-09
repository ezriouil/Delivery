package www.ezriouil.delivery.statistics.orders_refuser

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.R
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

class StatisticOrdersRefuserRV (private var myList:List<OrderInfo>,private val showNotes: ShowNotes) : RecyclerView.Adapter<StatisticOrdersRefuserRV.StatisticOrderRefuserVH>() {

    class StatisticOrderRefuserVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var time_confirmed: AppCompatTextView
        var time_refuser: AppCompatTextView
        var address: AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView
        var myLayout: LinearLayoutCompat

        init {
            name = itemView.findViewById(R.id.statistic_order_refuser_name) as AppCompatTextView
            address = itemView.findViewById(R.id.statistic_order_refuser_address) as AppCompatTextView
            time_confirmed = itemView.findViewById(R.id.statistic_order_refuser_time_confirmed) as AppCompatTextView
            time_refuser = itemView.findViewById(R.id.statistic_order_refuser_time_refuser) as AppCompatTextView
            price = itemView.findViewById(R.id.statistic_order_refuser_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.statistic_order_refuser_quantity) as AppCompatTextView
            myLayout = itemView.findViewById(R.id.statistic_order_refuser_layoutt) as LinearLayoutCompat
        }
    }

    override fun getItemCount() = myList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticOrderRefuserVH {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.statistic_orders_refuser_item,parent,false)
        return StatisticOrderRefuserVH(myView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: StatisticOrderRefuserVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.time_confirmed.text = "livery\n${it.timeSend.toString()}"
            holder.time_refuser.text = "Refuser\n${it.timePaid.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
        holder.myLayout.setOnLongClickListener {
            showNotes.showNote(myList[holder.adapterPosition].notes!!)
            return@setOnLongClickListener true
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}
interface ShowNotes{
    fun showNote(note : String)
}