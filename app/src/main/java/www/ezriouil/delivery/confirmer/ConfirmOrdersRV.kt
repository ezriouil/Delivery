package www.ezriouil.delivery.confirmer

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

class ConfirmOrdersRV(private var myList:List<OrderInfo>,private val allListener: AllListener) : RecyclerView.Adapter<ConfirmOrdersRV.ConfirmOrdersVH>() {
    class ConfirmOrdersVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var timeSend: AppCompatTextView
        var address: AppCompatTextView
        val timeRespond : AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView
        var btn_livery :AppCompatButton
        var btn_annuler :AppCompatButton

        init {
            name = itemView.findViewById(R.id.confirm_order_name) as AppCompatTextView
            timeSend = itemView.findViewById(R.id.confirm_order_time_send) as AppCompatTextView
            timeRespond = itemView.findViewById(R.id.confirm_order_time_response) as AppCompatTextView
            address = itemView.findViewById(R.id.confirm_order_address) as AppCompatTextView
            price = itemView.findViewById(R.id.confirm_order_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.confirm_order_quantity) as AppCompatTextView
            btn_livery = itemView.findViewById(R.id.confirm_order_btn_livery) as AppCompatButton
            btn_annuler = itemView.findViewById(R.id.confirm_order_btn_annuler) as AppCompatButton
        }

    }
    override fun getItemCount() = myList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= ConfirmOrdersVH(LayoutInflater.from(parent.context).inflate(R.layout.confirm_orders_item,parent,false))

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ConfirmOrdersVH, position: Int) {
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.timeSend.text = "envoyer\n${it.timeSend.toString()}"
            holder.timeRespond.text = "confirmer\n${it.timePaid.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
        holder.btn_livery.setOnClickListener { allListener.orderConfirm(myList[holder.adapterPosition]) }
        holder.btn_annuler.setOnClickListener { allListener.orderRefuser(myList[holder.adapterPosition]) }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}