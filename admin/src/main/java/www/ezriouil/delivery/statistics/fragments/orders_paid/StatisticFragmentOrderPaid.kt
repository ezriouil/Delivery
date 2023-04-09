package www.ezriouil.delivery.statistics.fragments.orders_paid

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
import www.ezriouil.delivery.databinding.StatisticFragmentOrderPaidBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.history_delivery_man.OrderInfoHistory
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

class StatisticFragmentOrderPaid : Fragment() , History{
    private lateinit var binding:StatisticFragmentOrderPaidBinding
    val dataOrderPaid = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticFragmentOrderPaidBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var total = 0
        val uid = arguments?.getString("nav3")
        val database = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uid.toString())
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                dataOrderPaid.clear()
                total = 0
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_PAID && itemData.isPaid == Constants.ORDER_IS_PAID_TRUE) {
                        total+=itemData.price
                        dataOrderPaid.add(itemData)
                    }
                }
                binding.total.text = "TOTALE : $total DH"
                val statisticOrderPaidRV = StatisticOrderPaidRV(dataOrderPaid.reversed(),this@StatisticFragmentOrderPaid)
                binding.statisticRvPaidOrders.adapter = statisticOrderPaidRV
                statisticOrderPaidRV.updateData(dataOrderPaid)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    override fun addToHistory(orderInfo: OrderInfo) {
        val uid = arguments?.getString("nav3")
        val alertDialog = AlertDialog.Builder(this@StatisticFragmentOrderPaid.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Supprime"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uid.toString()).child(orderInfo.id.toString()).removeValue().addOnSuccessListener {
                Firebase.database.getReference(Constants.ADMIN).child(Constants.HISTORY).child(orderInfo.to!!).push().setValue(OrderInfoHistory(orderInfo.name.toString(),orderInfo.address.toString(),orderInfo.price,orderInfo.timePaid.toString())).addOnSuccessListener {
                    Constants.toast(requireContext(),"La Commande à étè Supprime")
                }
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }
}


