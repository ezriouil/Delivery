package www.ezriouil.delivery.statistics.fragments.orders_plus_tard

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.R
import www.ezriouil.delivery.databinding.StatisticFragmentOrderPlusTardBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.DeliveryMenInfo
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo
import www.ezriouil.delivery.statistics.AllListenerOFStatistics
import www.ezriouil.delivery.statistics.fragments.orders_delivery_done.StatisticOrderLiveryDoneRV
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragmentOrderPlusTard : Fragment() , AllListenerOFStatistics {
    private lateinit var binding:StatisticFragmentOrderPlusTardBinding
    private val dataOrderDone = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticFragmentOrderPlusTardBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid = arguments?.getString("nav5")
        val database = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uid.toString())
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataOrderDone.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_PLUS_TARD) { dataOrderDone.add(itemData) }
                }
                val statisticOrderPlusTardRV = StatisticOrderPlusTardRV(dataOrderDone.reversed(),this@StatisticFragmentOrderPlusTard)
                binding.statisticRvPlusTard.adapter = statisticOrderPlusTardRV
                statisticOrderPlusTardRV.updateData(dataOrderDone)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun show(deliveryMenInfoUidAccount: String) {}
    override fun showAllOrdersRefuser(deliveryMenInfoUidAccount: String) {}
    override fun orderNoAnswerReSend(orderInfo: OrderInfo) {}
    override fun orderNoAnswerCancel(orderInfo: OrderInfo) {}
    override fun orderAppel(number: String) {}

    @SuppressLint("SimpleDateFormat")
    override fun orderPlusTardReSend(orderInfo: OrderInfo) {
        val timeNow = SimpleDateFormat("MM-dd HH:mm").format(Date())
        val alertDialog = AlertDialog.Builder(this@StatisticFragmentOrderPlusTard.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Renvoyer"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(orderInfo.to.toString()).child(orderInfo.id.toString()).setValue(
                OrderInfo(
                    orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_WAITING,orderInfo.isPaid,timeNow,"..."
                )
            ).addOnSuccessListener {
                Constants.toast(this@StatisticFragmentOrderPlusTard.requireContext(),"la commande à étè Renvoyer")
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }
}