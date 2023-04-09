package www.ezriouil.delivery.confirmer

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.AllListener
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.OrderInfo
import www.ezriouil.delivery.R
import www.ezriouil.delivery.databinding.FragmentConfirmerBinding
import java.text.SimpleDateFormat
import java.util.*

class FragmentConfirmed : Fragment() , AllListener{
    private lateinit var binding: FragmentConfirmerBinding
    private val data = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfirmerBinding.inflate(layoutInflater)
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
                    if (itemData?.status == Constants.ORDER_STATUS_CONFIRMED) data.add(itemData)
                }
                val confirmOrdersRV = ConfirmOrdersRV(data.reversed(),this@FragmentConfirmed)
                binding.confirmOrdersRv.adapter = confirmOrdersRV
                confirmOrdersRV.updateData(data)
            }
            override fun onCancelled(error: DatabaseError) {}

        })
    }

    @SuppressLint("SimpleDateFormat")
    override fun orderConfirm(orderInfo: OrderInfo) {
        val uidAccount = FirebaseAuth.getInstance().currentUser!!.uid
        val timeNow = SimpleDateFormat("MM/dd HH:mm").format(Date())
        val alertDialog = AlertDialog.Builder(this@FragmentConfirmed.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Livery"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount).child(orderInfo.id.toString()).setValue(
                OrderInfo(
                    orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_DONE,orderInfo.isPaid,orderInfo.timeSend!!,timeNow
                )
            ).addOnSuccessListener {
                Constants.toast(this@FragmentConfirmed.requireContext(),"la commande à étè livery")
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }

    @SuppressLint("SimpleDateFormat", "MissingInflatedId")
    override fun orderRefuser(orderInfo: OrderInfo) {
        val uidAccount = FirebaseAuth.getInstance().currentUser!!.uid
        val timeNow = SimpleDateFormat("MM/dd HH:mm").format(Date())
        val sheetDialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.bott_nav_cancel_notes,view?.findViewById(R.id.b_n_c_l))
        val myInput = view.findViewById<TextInputEditText>(R.id.b_n_c_n)
        val myBtn = view.findViewById<AppCompatButton>(R.id.b_n_c_btn)
        myBtn.setOnClickListener {
            if (myInput.text.toString().isNotBlank()){
                val alertDialog = AlertDialog.Builder(this@FragmentConfirmed.requireContext())
                alertDialog.setTitle("êtes-vous sûr !?")
                alertDialog.setMessage("command : ${orderInfo.name}")
                alertDialog.setPositiveButton("Refuser"){ _ , _ ->
                    Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount).child(orderInfo.id.toString()).setValue(
                        OrderInfo(
                            orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,myInput.text.toString(),Constants.ORDER_STATUS_CANCELED,orderInfo.isPaid,orderInfo.timeSend!!,timeNow
                        )
                    ).addOnSuccessListener {
                        Constants.toast(this@FragmentConfirmed.requireContext(),"la commande à étè refuser")
                        sheetDialog.dismiss()
                    }
                }
                alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
                alertDialog.show()
            } else Constants.toast(requireContext(),"Compléte Le Champ")
        }
        sheetDialog.setCancelable(true)
        sheetDialog.setContentView(view)
        sheetDialog.show()
    }

    override fun orderLiveryBtnPaid(orderInfo: OrderInfo) {}
    override fun orderWaitBtnConfirm(orderInfo: OrderInfo) {}
    override fun orderWaitBtnAnnuler(orderInfo: OrderInfo) {}
    override fun orderWaitBtnNoAnswer(orderInfo: OrderInfo) {}
    override fun orderWaitBtnCall(orderInfo: OrderInfo) {}
    override fun orderWaitBtnNotes(orderInfoNotes: String) {}
    override fun orderWaitBtnPlusTard(orderInfo: OrderInfo) {}

}