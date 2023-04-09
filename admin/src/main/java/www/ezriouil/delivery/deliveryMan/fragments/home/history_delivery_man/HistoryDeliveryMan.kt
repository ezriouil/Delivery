package www.ezriouil.delivery.deliveryMan.fragments.home.history_delivery_man

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.databinding.HistoryDeliveryManBinding

class HistoryDeliveryMan : AppCompatActivity() {
    private lateinit var binding:HistoryDeliveryManBinding
    private val data = mutableListOf<OrderInfoHistory>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = HistoryDeliveryManBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val uid = intent.getStringExtra("uid")
        val database = FirebaseDatabase.getInstance().getReference(Constants.ADMIN).child(Constants.HISTORY).child(uid!!)
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfoHistory::class.java)
                    data.add(itemData!!)
                }
                val historyDeliveryManRV = HistoryDeliveryManRV(data.reversed())
                binding.historyDeliveryManRv.adapter = historyDeliveryManRV
                historyDeliveryManRV.updateData(data)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}