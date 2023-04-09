package www.ezriouil.delivery.deliveryMan.fragments.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.R
import www.ezriouil.delivery.databinding.DeliveryMenFragmentHomeBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.history_delivery_man.HistoryDeliveryMan
import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.NewOrder

class DeliveryMenFragmentHome : Fragment() , AllListener{
    private lateinit var binding: DeliveryMenFragmentHomeBinding
    private val data = ArrayList<DeliveryMenInfo>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = DeliveryMenFragmentHomeBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val deliveryManHomeRV = DeliveryManHomeRV(data,this@DeliveryMenFragmentHome)
        val database = FirebaseDatabase.getInstance().getReference(Constants.ADMIN).child(Constants.DELIVERY_MEN)
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in snapshot.children){
                    val itemData = item.getValue(DeliveryMenInfo::class.java)
                    data.add(itemData!!)
                }
                binding.deliveryManFragmentHomeRV.adapter = deliveryManHomeRV
                deliveryManHomeRV.updateData(data)
            }
            override fun onCancelled(error: DatabaseError) {}
        })
    }

    @SuppressLint("CutPasteId", "MissingInflatedId", "SetTextI18n")
    override fun sendOrder(deliveryMenInfo: DeliveryMenInfo) {
        val intent = Intent(requireContext(),NewOrder::class.java)
        intent.putExtra("1", deliveryMenInfo.userName)
        intent.putExtra("2",deliveryMenInfo.uIdAccount)
        startActivity(intent)
    }

    override fun history(deliveryMenUid: String) {
        val intent = Intent(requireContext(),HistoryDeliveryMan::class.java)
        intent.putExtra("uid",deliveryMenUid)
        startActivity(intent)
    }

    @SuppressLint("MissingInflatedId")
    override fun showAllInfo(deliveryMenInfo: DeliveryMenInfo) {
        val alertDialog = AlertDialog.Builder(requireContext()).create()
        val myView = LayoutInflater.from(requireContext()).inflate(R.layout.delivery_man_show_all_info,null)
        myView.findViewById<AppCompatImageView>(R.id.delivery_men_info_btn_close).setOnClickListener { alertDialog.dismiss() }
        myView.findViewById<AppCompatTextView>(R.id.delivery_men_info_userName).text = deliveryMenInfo.userName.toString().uppercase()
        myView.findViewById<AppCompatTextView>(R.id.delivery_men_info_gmail).text = deliveryMenInfo.gmail.toString().uppercase()
        myView.findViewById<AppCompatTextView>(R.id.delivery_men_info_password).text = deliveryMenInfo.password.toString().uppercase()
        myView.findViewById<AppCompatTextView>(R.id.delivery_men_info_city).text = deliveryMenInfo.city.toString().uppercase()
        myView.findViewById<AppCompatTextView>(R.id.delivery_men_info_street).text = deliveryMenInfo.street.toString().uppercase()
        myView.findViewById<AppCompatTextView>(R.id.delivery_men_info_number).text = deliveryMenInfo.number.toString().uppercase()
        myView.findViewById<AppCompatTextView>(R.id.delivery_men_info_timeRegistered).text = deliveryMenInfo.timeRegistered.toString().uppercase()
        alertDialog.setView(myView)
        alertDialog.show()
    }
}