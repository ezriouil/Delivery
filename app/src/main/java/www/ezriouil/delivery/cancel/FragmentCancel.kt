package www.ezriouil.delivery.cancel

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
import www.ezriouil.delivery.databinding.FragmentCancelBinding

class FragmentCancel : Fragment() {
    private lateinit var binding: FragmentCancelBinding
    private val data = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCancelBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uidAccount = FirebaseAuth.getInstance().currentUser!!.uid
        val fireBase = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount)
        fireBase.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_CANCELED) data.add(itemData)
                }
                val cancelOrdersRV = CancelOrdersRV(data.reversed())
                binding.cancelOrdersRv.adapter = cancelOrdersRV
                cancelOrdersRV.updateData(data)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

}