package www.ezriouil.delivery.statistics.fragments.orders_delivery_done

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.databinding.StatisticFragmentOrderDeliveryDoneBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

class StatisticFragmentOrderDeliveryDone : Fragment() {
    private lateinit var binding:StatisticFragmentOrderDeliveryDoneBinding
    private val dataOrderDone = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticFragmentOrderDeliveryDoneBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid = arguments?.getString("nav4")
        val database = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uid.toString())
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataOrderDone.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_DONE && itemData.isPaid == Constants.ORDER_IS_PAID_FALSE) { dataOrderDone.add(itemData) }
                }
                val statisticOrderPaidRV = StatisticOrderLiveryDoneRV(dataOrderDone.reversed())
                binding.statisticRvDeliveryDoneOrders.adapter = statisticOrderPaidRV
                statisticOrderPaidRV.updateData(dataOrderDone)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}