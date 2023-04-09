package www.ezriouil.delivery.paid

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.OrderInfo
import www.ezriouil.delivery.databinding.FragmentPaidBinding

class FragmentPaid : Fragment() {
    private lateinit var binding: FragmentPaidBinding
    private val data = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentPaidBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var total = 0
        val uidAccount = FirebaseAuth.getInstance().currentUser!!.uid
        val fireBase = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount)
        fireBase.addValueEventListener(object : ValueEventListener {
            @SuppressLint("SetTextI18n")
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                total = 0
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_PAID){
                        data.add(itemData)
                        total+=itemData.price
                    }
                }
                binding.total.text = "TOTALE : $total DH"
                val paidOrdersRV = PaidOrdersRV(data.reversed())
                binding.paidOrdersRv.adapter = paidOrdersRV
                paidOrdersRV.updateData(data)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }
}