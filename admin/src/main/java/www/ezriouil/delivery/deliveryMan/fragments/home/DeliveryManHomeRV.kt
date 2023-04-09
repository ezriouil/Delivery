package www.ezriouil.delivery.deliveryMan.fragments.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import www.ezriouil.delivery.R

class DeliveryManHomeRV(private var myList:List<DeliveryMenInfo>,private val allListener: AllListener) : Adapter<DeliveryManHomeRV.DeliveryManHomeVH>() {
    class DeliveryManHomeVH(itemView: View):ViewHolder(itemView){

        var img: CircleImageView
        var userName:AppCompatTextView
        var city:AppCompatTextView
        var street:AppCompatTextView
        var number:AppCompatTextView
        val btnSendOrder:AppCompatButton
        val btnShowAllInfo:AppCompatButton
        val layout:LinearLayoutCompat
        //val slideImages:ImageSlider

        init {
            userName = itemView.findViewById(R.id.deliveryMan_item_rv_userName) as AppCompatTextView
            city = itemView.findViewById(R.id.deliveryMan_item_rv_city) as AppCompatTextView
            street = itemView.findViewById(R.id.deliveryMan_item_rv_street) as AppCompatTextView
            number = itemView.findViewById(R.id.deliveryMan_item_rv_number) as AppCompatTextView
            btnSendOrder = itemView.findViewById(R.id.deliveryMan_item_rv_btnSendOrder) as AppCompatButton
            btnShowAllInfo = itemView.findViewById(R.id.deliveryMan_item_rv_btnShowAllInfo) as AppCompatButton
            layout = itemView.findViewById(R.id.deliveryMan_item_rv_layout) as LinearLayoutCompat
            img = itemView.findViewById(R.id.deliveryMan_item_rv_img) as CircleImageView
            //slideImages = itemView.findViewById(R.id.imageSlide) as ImageSlider
        }

    }

    override fun getItemCount() = myList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeliveryManHomeVH {
        val myView = LayoutInflater.from(parent.context).inflate(R.layout.delivery_man_home_item_rv,parent,false)
        return DeliveryManHomeVH(myView)
    }

    override fun onBindViewHolder(holder: DeliveryManHomeVH, position: Int) {
        myList[holder.adapterPosition].let {
            Picasso.get().load(it.img).apply {
                placeholder(R.drawable.user_par_default)
                into(holder.img)
                centerCrop()
                error(R.drawable.user_par_default)
            }
            //holder.slideImages.setImageList(arrayListOf(SlideModel(it.img),SlideModel("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTffP2UyJroGs4V9OoON1AF9k0p04uPSPxwWbDnPv_IdvL49DFzv4mzYNI92pugXM5Hp9I&usqp=CAU"),SlideModel(it.img)))
            holder.userName.text = it.userName.toString().uppercase()
            holder.city.text = it.city.toString().uppercase()
            holder.street.text = it.street.toString().uppercase()
            holder.number.text = it.number
        }
        holder.btnSendOrder.setOnClickListener { allListener.sendOrder(myList[holder.adapterPosition]) }
        holder.btnShowAllInfo.setOnClickListener { allListener.showAllInfo(myList[holder.adapterPosition]) }
        holder.layout.setOnLongClickListener {
            allListener.history(myList[holder.adapterPosition].uIdAccount!!)
            return@setOnLongClickListener true
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newList:List<DeliveryMenInfo>){
        myList = newList
        notifyDataSetChanged()
    }
}

