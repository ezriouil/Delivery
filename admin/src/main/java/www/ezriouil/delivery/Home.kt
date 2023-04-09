package www.ezriouil.delivery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import www.ezriouil.delivery.databinding.HomeBinding
import www.ezriouil.delivery.deliveryMan.DeliveryMan
import www.ezriouil.delivery.statistics.Statistics

class Home : AppCompatActivity() {
    private lateinit var binding: HomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = HomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.HomeBtnDeliveryMan.setOnClickListener { startActivity(Intent(this,DeliveryMan::class.java)) }
        binding.HomeBtnStatic.setOnClickListener { startActivity(Intent(this, Statistics::class.java)) }
        binding.homeBtnClose.setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@Home)
            alertDialog.setTitle("êtes-vous sûr !?")
            alertDialog.setMessage("Sortie")
            alertDialog.setPositiveButton("Oui"){ _ , _ ->
                this.finish()
            }
            alertDialog.setNegativeButton("Non"){ _ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
            alertDialog.show() }
    }
}