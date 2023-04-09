package www.ezriouil.delivery.deliveryMan.fragments.user

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import www.ezriouil.delivery.Constants
import www.ezriouil.delivery.databinding.DeliveryMenFragmentUserBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.DeliveryMenInfo
import java.text.SimpleDateFormat
import java.util.*

class DeliveryMenFragmentUser : Fragment() {
    private lateinit var binding: DeliveryMenFragmentUserBinding
    private var picUri = "https://st3.depositphotos.com/3581215/18899/v/600/depositphotos_188994514-stock-illustration-vector-illustration-male-silhouette-profile.jpg"
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View{
        binding = DeliveryMenFragmentUserBinding.inflate(inflater)
        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val firebaseAuth = FirebaseAuth.getInstance()
        val database = Firebase.database.getReference(Constants.ADMIN).child(Constants.DELIVERY_MEN).push()
        val pic=binding.newDeliveryManPic
        val userName=binding.newDeliveryManUserName
        val gmail=binding.newDeliveryManGmail
        val password=binding.newDeliveryManPassword
        val city=binding.newDeliveryManCity
        val street=binding.newDeliveryManStreet
        val number=binding.newDeliveryManNumber
        val timeNow = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
        pic.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent,100)
        }
        binding.newDeliveryManBtnRegister.setOnClickListener {
            if (userName.text.toString().isNotBlank() && gmail.text.toString().isNotBlank() && password.text.toString().isNotBlank() && city.text.toString().isNotBlank() && street.text.toString().isNotBlank() && number.text.toString().isNotBlank()){
                firebaseAuth.createUserWithEmailAndPassword(gmail.text.toString().lowercase(),password.text.toString().lowercase()).addOnCompleteListener {
                    if (it.isSuccessful)
                    {
                        val deliveryMenInfo = DeliveryMenInfo(database.key.toString(),firebaseAuth.uid.toString(),picUri,userName.text.toString(),gmail.text.toString(),password.text.toString(),city.text.toString(),street.text.toString(),number.text.toString(),timeNow)
                        database.setValue(deliveryMenInfo)
                        //Firebase.database.getReference(Constants.ADMIN).child(Constants.DELIVERY_MEN_IMG).child(firebaseAuth.uid.toString()).push().setValue(picUri)
                        userName.text?.clear()
                        gmail.text?.clear()
                        password.text?.clear()
                        city.text?.clear()
                        street.text?.clear()
                        number.text?.clear()
                        Constants.toast(requireContext(),"Inscription réussie")
                    }
                    else { Constants.toast(requireContext(),"Erreur 404") }
                }

            }else Constants.toast(requireContext(),"Les champs vide")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode==100 && resultCode== AppCompatActivity.RESULT_OK && data != null){
            val firebaseStore = Firebase.storage.getReference(System.currentTimeMillis().toString())
            firebaseStore.putFile(data.data!!).addOnSuccessListener {
                firebaseStore.downloadUrl.addOnSuccessListener {
                    binding.newDeliveryManPic.setImageURI(data.data)
                    picUri = it.toString()
                    Constants.toast(requireContext(),"l'image à étè télécharge")
                }
            }
        }
    }
}