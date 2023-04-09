package www.ezriouil.delivery.statistics.fragments.orders_no_answer

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
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
import www.ezriouil.delivery.databinding.StatisticFragmentOrderNoAnswerBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.DeliveryMenInfo
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo
import www.ezriouil.delivery.statistics.AllListenerOFStatistics
import java.text.SimpleDateFormat
import java.util.*

class StatisticFragmentOrderNoAnswer : Fragment() , AllListenerOFStatistics {
    private lateinit var binding:StatisticFragmentOrderNoAnswerBinding
    val dataOrdersNoAnswer = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticFragmentOrderNoAnswerBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid = arguments?.getString("nav2")
        val database = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uid.toString())
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataOrdersNoAnswer.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_NO_ANSWER)  dataOrdersNoAnswer.add(itemData)
                }
                val statisticOrderNoAnswerRV = StatisticOrderNoAnswerRV(dataOrdersNoAnswer.reversed(),this@StatisticFragmentOrderNoAnswer)
                binding.statisticRvNoAnswerOrders.adapter = statisticOrderNoAnswerRV
                statisticOrderNoAnswerRV.updateData(dataOrdersNoAnswer)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    override fun show(deliveryMenInfoUidAccount: String) {}
    override fun showAllOrdersRefuser(deliveryMenInfoUidAccount: String) {}

    @SuppressLint("SimpleDateFormat")
    override fun orderNoAnswerReSend(orderInfo: OrderInfo) {
        val timeNow = SimpleDateFormat("MM-dd HH:mm").format(Date())
        val alertDialog = AlertDialog.Builder(this@StatisticFragmentOrderNoAnswer.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Renvoyer"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(orderInfo.to.toString()).child(orderInfo.id.toString()).setValue(
                OrderInfo(
                    orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_WAITING,orderInfo.isPaid,timeNow,orderInfo.timePaid!!
                )
            ).addOnSuccessListener {
                Constants.toast(this@StatisticFragmentOrderNoAnswer.requireContext(),"la commande à étè renvoyer")
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }
    override fun orderNoAnswerCancel(orderInfo: OrderInfo) {
        val alertDialog = AlertDialog.Builder(this@StatisticFragmentOrderNoAnswer.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Pas de réponse"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(orderInfo.to.toString()).child(orderInfo.id.toString()).setValue(
                OrderInfo(
                    orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_CANCELED,orderInfo.isPaid,orderInfo.timeSend!!,orderInfo.timePaid!!
                )
            ).addOnSuccessListener {
                Constants.toast(this@StatisticFragmentOrderNoAnswer.requireContext(),"la commande à étè annuler")
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }

    override fun orderAppel(number: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
    }

    override fun orderPlusTardReSend(orderInfo: OrderInfo) {}
}