package www.ezriouil.delivery.statistics

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import www.ezriouil.delivery.R
import www.ezriouil.delivery.deliveryMan.fragments.home.DeliveryMenInfo

class StatisticDeliveryManRV(private var myList:List<DeliveryMenInfo>,private val allListenerOFStatistics: AllListenerOFStatistics) : RecyclerView.Adapter<StatisticDeliveryManRV.StatisticDeliveryManVH>() {
    class StatisticDeliveryManVH(itemView: View): RecyclerView.ViewHolder(itemView){

        var img: CircleImageView
        var userName: TextView
        var city: TextView
        var street: TextView
        var number: TextView
        var btnShow:AppCompatButton
        var btnShowOrdersRefuser:LinearLayoutCompat

        init {
            userName = itemView.findViewById(R.id.statistics_delivery_men_userName) as TextView
            city = itemView.findViewById(R.id.statistics_delivery_men_city) as TextView
            street = itemView.findViewById(R.id.statistics_delivery_men_street) as TextView
            number = itemView.findViewById(R.id.statistics_delivery_men_number) as TextView
            img = itemView.findViewById(R.id.statistics_delivery_men_img) as CircleImageView
            btnShow = itemView.findViewById(R.id.statistics_delivery_men_btn_show) as AppCompatButton
            btnShowOrdersRefuser = itemView.findViewById(R.id.statistics_delivery_men_show_orders_refuser) as LinearLayoutCompat
        }

    }

    override fun getItemCount() = myList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticDeliveryManVH {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.statistic_delivery_men_item_rv,parent,false)
        return StatisticDeliveryManVH(myView)
    }

    override fun onBindViewHolder(holder: StatisticDeliveryManVH, position: Int) {
        myList[holder.adapterPosition].let {
            Picasso.get().load(it.img).apply {
                into(holder.img)
                centerCrop()
                fit()
                placeholder(R.drawable.user_par_default)
            }
            holder.userName.text = it.userName.toString().uppercase()
            holder.city.text = it.city.toString().uppercase()
            holder.street.text = it.street.toString().uppercase()
            holder.number.text = it.number
        }
        holder.btnShow.setOnClickListener { allListenerOFStatistics.show(myList[holder.adapterPosition].uIdAccount!!) }
        holder.btnShowOrdersRefuser.setOnLongClickListener {
            allListenerOFStatistics.showAllOrdersRefuser(myList[holder.adapterPosition].uIdAccount!!)
            return@setOnLongClickListener true
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun update(newList:ArrayList<DeliveryMenInfo>){
        myList = newList
        notifyDataSetChanged()
    }
}