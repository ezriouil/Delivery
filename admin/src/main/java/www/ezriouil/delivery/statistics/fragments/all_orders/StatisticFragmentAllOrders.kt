package www.ezriouil.delivery.statistics.fragments.all_orders

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
import www.ezriouil.delivery.databinding.StatisticFragmentAllOrdersBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

class StatisticFragmentAllOrders : Fragment() {

    private lateinit var binding:StatisticFragmentAllOrdersBinding
    val dataAllOrders = mutableListOf<OrderInfo>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = StatisticFragmentAllOrdersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uid = arguments?.getString("nav1")
        val database = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uid.toString())
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataAllOrders.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_CONFIRMED ||
                        itemData?.status == Constants.ORDER_STATUS_ANNULER ||
                        itemData?.status == Constants.ORDER_STATUS_WAITING)
                    { dataAllOrders.add(itemData) }
                }
                val statisticOrderRV = StatisticOrderRV(dataAllOrders.reversed())
                binding.statisticRvAllOrders.adapter = statisticOrderRV
                statisticOrderRV.updateData(dataAllOrders)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}