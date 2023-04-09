package www.ezriouil.delivery.deliveryMan.fragments.home.new_order

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.databinding.NewOrderBinding
import java.text.SimpleDateFormat
import java.util.*

class NewOrder : AppCompatActivity() {
    private lateinit var binding: NewOrderBinding
    @SuppressLint("SimpleDateFormat", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = NewOrderBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val nameOfDeliveryMen=intent.getStringExtra("1")
        val uidOfDeliveryMen=intent.getStringExtra("2")
        val database = Firebase.database
        binding.newOrderBtnSend.setOnClickListener {
            val pushOrder =database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidOfDeliveryMen!!).push()
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setTitle("Commande")
            alertDialog.setMessage("êtes-vous sûr envoyer la commande à $nameOfDeliveryMen !? : ")
            alertDialog.setPositiveButton("Oui"){ _ , _ ->
                if (
                    binding.newOrderName.text!!.isNotBlank() &&
                    binding.newOrderPrice.text!!.isNotBlank() &&
                    binding.newOrderQuantity.text!!.isNotBlank() &&
                    binding.newOrderAddress.text!!.isNotBlank() &&
                    binding.newOrderCity.text!!.isNotBlank() &&
                    binding.newOrderPhone.text!!.isNotBlank() &&
                    binding.newOrderNotes.text!!.isNotBlank())
                {
                    val timeNow = SimpleDateFormat("MM/dd HH:mm").format(Date())
                    val orderInfo = OrderInfo(
                        pushOrder.key.toString(),
                        uidOfDeliveryMen,
                        binding.newOrderName.text.toString(),
                        binding.newOrderCity.text.toString()+" "+binding.newOrderAddress.text.toString(),
                        binding.newOrderPrice.text.toString().toInt(),
                        binding.newOrderQuantity.text.toString().toInt(),
                        binding.newOrderPhone.text.toString(),
                        binding.newOrderNotes.text.toString(),
                        Constants.ORDER_STATUS_WAITING,
                        Constants.ORDER_IS_PAID_FALSE,
                        timeNow,
                        Constants.ORDER_TIME__RESPONSE,
                    )
                    pushOrder.setValue(orderInfo).addOnSuccessListener { clearText() }
                } else Constants.toast(this,"Les champs est vide")
            }
            alertDialog.setNegativeButton("Non"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
            alertDialog.show()
        }
    }

    private fun clearText() {
        binding.newOrderName.text?.clear()
        binding.newOrderAddress.text?.clear()
        binding.newOrderPrice.text?.clear()
        binding.newOrderQuantity.text?.clear()
        binding.newOrderCity.text?.clear()
        binding.newOrderPhone.text?.clear()
        binding.newOrderNotes.text?.clear()
        Constants.toast(this,"Command à étè envoyer")
    }
}