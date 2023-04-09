package www.ezriouil.delivery.waiting

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import www.ezriouil.delivery.AllListener
import www.ezriouil.delivery.OrderInfo
import www.ezriouil.delivery.R

class WaitingOrdersRV(private val context: Context,private var myList:List<OrderInfo>,private val allListener: AllListener) : RecyclerView.Adapter<WaitingOrdersRV.LiveryOrdersVH>() {
    class LiveryOrdersVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var name: AppCompatTextView
        var timeSend: AppCompatTextView
        var address: AppCompatTextView
        var price: AppCompatTextView
        var quantity: AppCompatTextView
        var btn_confirm :Button
        var btn_cancel :Button
        var btn_no_answer :Button
        var btn_appel :Button
        val btn_notes : Button
        val btn_plus_tard : Button
        val btn_show_all_btn : AppCompatImageView
        var btnGone1 : LinearLayoutCompat
        var btnGone2 : LinearLayoutCompat
        var btnGone3 : LinearLayoutCompat
        var cardWithLayout : LinearLayoutCompat

        init {
            name = itemView.findViewById(R.id.waiting_order_name) as AppCompatTextView
            timeSend = itemView.findViewById(R.id.waiting_order_time_send) as AppCompatTextView
            address = itemView.findViewById(R.id.waiting_order_address) as AppCompatTextView
            price = itemView.findViewById(R.id.waiting_order_price) as AppCompatTextView
            quantity = itemView.findViewById(R.id.waiting_order_quantity) as AppCompatTextView
            btn_confirm = itemView.findViewById(R.id.waiting_order_btn_confirm) as Button
            btn_cancel = itemView.findViewById(R.id.waiting_order_btn_cancel) as Button
            btn_no_answer = itemView.findViewById(R.id.waiting_order_btn_no_answer) as Button
            btn_appel = itemView.findViewById(R.id.waiting_order_btn_appel) as Button
            btn_notes = itemView.findViewById(R.id.waiting_order_btn_notes) as Button
            btn_plus_tard = itemView.findViewById(R.id.waiting_order_btn_plus_tard) as Button
            btn_show_all_btn = itemView.findViewById(R.id.waiting_order_btn_show_all_btn) as AppCompatImageView
            btnGone1 = itemView.findViewById(R.id.btn_gone1) as LinearLayoutCompat
            btnGone2 = itemView.findViewById(R.id.btn_gone2) as LinearLayoutCompat
            btnGone3 = itemView.findViewById(R.id.btn_gone3) as LinearLayoutCompat
            cardWithLayout = itemView.findViewById(R.id.cardWithLayout) as LinearLayoutCompat
        }
    }
    override fun getItemCount()=myList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=LiveryOrdersVH(LayoutInflater.from(parent.context).inflate(R.layout.waiting_orders_item,parent,false))
    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LiveryOrdersVH, position: Int) {
        val animationUtils1 = AnimationUtils.loadAnimation(context,R.anim.animation_btn1)
        val animationUtils2 = AnimationUtils.loadAnimation(context,R.anim.animation_btn2)
        val animationUtils3 = AnimationUtils.loadAnimation(context,R.anim.animation_btn3)
        myList[holder.adapterPosition].let {
            holder.name.text = it.name.toString().uppercase()
            holder.timeSend.text = "envoyer\n${it.timeSend.toString()}"
            holder.address.text = it.address.toString().uppercase()
            holder.price.text = "prix\n${it.price} DH"
            holder.quantity.text = "quantité\n${it.quantity}"
        }
        holder.btn_show_all_btn.setOnClickListener{
            if (holder.btnGone1.visibility == View.GONE &&
                holder.btnGone2.visibility == View.GONE &&
                holder.btnGone3.visibility == View.GONE)
            {
                holder.btnGone1.visibility = View.VISIBLE
                holder.btnGone2.visibility = View.VISIBLE
                holder.btnGone3.visibility = View.VISIBLE
                holder.btnGone1.startAnimation(animationUtils1)
                holder.btnGone2.startAnimation(animationUtils2)
                holder.btnGone3.startAnimation(animationUtils3)
                holder.cardWithLayout.animate().scaleX(1.02f).scaleY(1.02f).duration = 700
                holder.btn_show_all_btn.setImageResource(R.drawable.icon_annuler)
            }
            else
            {
                holder.btnGone1.visibility = View.GONE
                holder.btnGone2.visibility = View.GONE
                holder.btnGone3.visibility = View.GONE
                holder.cardWithLayout.animate().scaleX(1f).scaleY(1f).duration = 700
                holder.btn_show_all_btn.setImageResource(R.drawable.close)
            }
        }
        holder.btn_confirm.setOnClickListener { allListener.orderWaitBtnConfirm(myList[holder.adapterPosition]) }
        holder.btn_cancel.setOnClickListener { allListener.orderWaitBtnAnnuler(myList[holder.adapterPosition]) }
        holder.btn_no_answer.setOnClickListener { allListener.orderWaitBtnNoAnswer(myList[holder.adapterPosition]) }
        holder.btn_appel.setOnClickListener { allListener.orderWaitBtnCall(myList[holder.adapterPosition]) }
        holder.btn_notes.setOnClickListener { allListener.orderWaitBtnNotes(myList[holder.adapterPosition].notes!!) }
        holder.btn_plus_tard.setOnClickListener { allListener.orderWaitBtnPlusTard(myList[holder.adapterPosition]) }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:MutableList<OrderInfo>){
        myList = newList.reversed()
        notifyDataSetChanged()
    }
}