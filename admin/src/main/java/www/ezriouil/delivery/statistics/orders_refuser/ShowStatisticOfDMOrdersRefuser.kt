package www.ezriouil.delivery.statistics.orders_refuser

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatTextView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.R
import www.ezriouil.delivery.databinding.ShowStatisticOfDeliveryManOrdersRefuserBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

class ShowStatisticOfDMOrdersRefuser : AppCompatActivity() , ShowNotes {
    private lateinit var binding:ShowStatisticOfDeliveryManOrdersRefuserBinding
    val dataOrderPaid = mutableListOf<OrderInfo>()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ShowStatisticOfDeliveryManOrdersRefuserBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.closeActivityOfOrdersRefused.setOnClickListener { this.finish() }
        val uidAccount = intent.getStringExtra("deliveryMenInfoUidAccount")
        val database = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount.toString())
        database.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                dataOrderPaid.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_CANCELED) {
                        dataOrderPaid.add(itemData)
                    }
                }
                val statisticOrdersRefuserRV = StatisticOrdersRefuserRV(dataOrderPaid.reversed(),this@ShowStatisticOfDMOrdersRefuser)
                binding.statisticRvRefuserOrders.adapter = statisticOrdersRefuserRV
                statisticOrdersRefuserRV.updateData(dataOrderPaid)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    @SuppressLint("InflateParams", "MissingInflatedId")
    override fun showNote(note: String) {
        val sheetDialogNotes = BottomSheetDialog(this@ShowStatisticOfDMOrdersRefuser)
        val view = LayoutInflater.from(this@ShowStatisticOfDMOrdersRefuser).inflate(R.layout.bott_nav_refuser_notes,null)
        view.findViewById<AppCompatTextView>(R.id.bot_nav_refuser_note_notes).text = note
        sheetDialogNotes.setContentView(view)
        sheetDialogNotes.show()
    }
}