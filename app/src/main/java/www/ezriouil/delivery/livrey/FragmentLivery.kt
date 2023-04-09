package www.ezriouil.delivery.livrey

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.AllListener
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.OrderInfo

import www.ezriouil.delivery.databinding.FragmentLivreyBinding
import java.text.SimpleDateFormat
import java.util.*

class FragmentLivery : Fragment() , AllListener{
    private lateinit var binding:FragmentLivreyBinding
    private val data = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentLivreyBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val uidAccount = FirebaseAuth.getInstance().currentUser!!.uid
        val fireBase = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount)
        fireBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_DONE) data.add(itemData)
                }
                val liveryOrdersRV = LiveryOrdersRV(data.reversed(),this@FragmentLivery)
                binding.liveryOrdersRv.adapter = liveryOrdersRV
                liveryOrdersRV.updateData(data)
            }
            override fun onCancelled(error: DatabaseError) {}

        })
    }

    @SuppressLint("SimpleDateFormat")
    override fun orderLiveryBtnPaid(orderInfo: OrderInfo) {
        val uidAccount = FirebaseAuth.getInstance().currentUser!!.uid
        val timeNow = SimpleDateFormat("MM/dd HH:mm").format(Date())
        val alertDialog = AlertDialog.Builder(this@FragmentLivery.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Payé"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount).child(orderInfo.id.toString()).setValue(
                OrderInfo(
                    orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_PAID,Constants.ORDER_IS_PAID_TRUE,orderInfo.timePaid!!,timeNow
                )
            ).addOnSuccessListener {
                Constants.toast(this@FragmentLivery.requireContext(),"la commande à étè payée")
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }

    override fun orderWaitBtnConfirm(orderInfo: OrderInfo) {}
    override fun orderWaitBtnAnnuler(orderInfo: OrderInfo) {}
    override fun orderWaitBtnNoAnswer(orderInfo: OrderInfo) {}
    override fun orderWaitBtnCall(orderInfo: OrderInfo) {}
    override fun orderWaitBtnNotes(orderInfoNotes: String) {}
    override fun orderWaitBtnPlusTard(orderInfo: OrderInfo) {}

    override fun orderConfirm(orderInfo: OrderInfo) {}
    override fun orderRefuser(orderInfo: OrderInfo) {}
}