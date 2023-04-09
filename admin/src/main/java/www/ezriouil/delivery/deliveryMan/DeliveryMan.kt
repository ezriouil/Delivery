package www.ezriouil.delivery.deliveryMan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import www.ezriouil.delivery.R
import www.ezriouil.delivery.databinding.DeliveryManBinding
import www.ezriouil.delivery.deliveryMan.fragments.home.DeliveryMenFragmentHome
import www.ezriouil.delivery.deliveryMan.fragments.user.DeliveryMenFragmentUser

class DeliveryMan : AppCompatActivity() {
    private lateinit var binding: DeliveryManBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DeliveryManBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(DeliveryMenFragmentHome())
        binding.deliveryManBottomNavigationView.setItemSelected(R.id.deliveryMan_home_nav,true)

        binding.deliveryManBottomNavigationView.setOnItemSelectedListener {
            when(it){
                R.id.deliveryMan_home_nav -> {replaceFragment(DeliveryMenFragmentHome())}
                R.id.deliveryMan_user_nav -> {replaceFragment(DeliveryMenFragmentUser())}
            } }
    }
    private fun replaceFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.deliveryManFrameLayout.id,fragment)
            commit()
        }
    }
}