package www.ezriouil.delivery.statistics

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.databinding.StatisticsBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.DeliveryMenInfo
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo
import www.ezriouil.delivery.statistics.orders_refuser.ShowStatisticOfDMOrdersRefuser

class Statistics : AppCompatActivity() , AllListenerOFStatistics{
    private lateinit var binding: StatisticsBinding
    val dataDeliveryMan = ArrayList<DeliveryMenInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = StatisticsBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val database = Firebase.database.getReference(Constants.ADMIN).child(Constants.DELIVERY_MEN)
        database.addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                dataDeliveryMan.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(DeliveryMenInfo::class.java)
                    dataDeliveryMan.add(itemData!!)
                }
                val statisticDeliveryManRV = StatisticDeliveryManRV(dataDeliveryMan,this@Statistics)
                binding.statisticRv.adapter = statisticDeliveryManRV
                statisticDeliveryManRV.update(dataDeliveryMan)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
    override fun show(deliveryMenInfoUidAccount: String) {
        val intent = Intent(this,ShowStatisticOfDeliveryMan::class.java)
        intent.putExtra("3",deliveryMenInfoUidAccount)
        startActivity(intent)
    }
    override fun showAllOrdersRefuser(deliveryMenInfoUidAccount: String) {
        val intent = Intent(this, ShowStatisticOfDMOrdersRefuser::class.java)
        intent.putExtra("deliveryMenInfoUidAccount",deliveryMenInfoUidAccount)
        startActivity(intent)
    }
    override fun orderNoAnswerReSend(orderInfo: OrderInfo) {}
    override fun orderNoAnswerCancel(orderInfo: OrderInfo) {}
    override fun orderAppel(number: String) {}
    override fun orderPlusTardReSend(orderInfo: OrderInfo) {}
}




