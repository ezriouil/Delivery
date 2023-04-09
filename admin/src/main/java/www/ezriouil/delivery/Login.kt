package www.ezriouil.delivery

import android.content.Intent
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.auth.FirebaseAuth
import www.ezriouil.delivery.databinding.LoginBinding

@Suppress("DEPRECATION")
class Login : AppCompatActivity() {
    private lateinit var binding: LoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = LoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val sharedPreferences = getSharedPreferences(Constants.S_P_LOGIN,MODE_PRIVATE)
        binding.loginEmail.setText(sharedPreferences.getString(Constants.S_P_EMAIL,""))
        binding.loginPassword.setText(sharedPreferences.getString(Constants.S_P_PASSWORD,""))
        val firebaseAuth = FirebaseAuth.getInstance()
        val editTextEmail = binding.loginEmail
        val editTextPassword = binding.loginPassword

        if (isConnected()) {
            binding.loginBtn.setOnClickListener {
                val email = editTextEmail.text
                val password = editTextPassword.text
                if (email.toString().isNotBlank() && password.toString().isNotBlank()){
                    if (email.toString().trim().lowercase() == Constants.ADMIN_GMAIL && password.toString().trim().lowercase()  == Constants.ADMIN_PASSWORD){
                        firebaseAuth.signInWithEmailAndPassword(email.toString().lowercase(),password.toString().lowercase()).addOnCompleteListener {
                            if (it.isSuccessful){
                                sharedPreferences.edit().apply {
                                    this.putString(Constants.S_P_EMAIL,email.toString())
                                    this.putString(Constants.S_P_PASSWORD,password.toString())
                                }.apply()
                                val intent = Intent(this@Login, Home::class.java)
                                startActivity(intent)
                                this.finish()
                            } else Constants.toast(this@Login,"SVP ! recommence")
                        }
                    } else Constants.toast(this@Login,"Erreur404")
                } else Constants.toast(this@Login,"Les Champs Est Vide")
            }
            Toast.makeText(this,"Connected", Toast.LENGTH_LONG).show()
        }
        else {
            val alertDialog = AlertDialog.Builder(this,R.style.bottomSheetStyle)
            val view = layoutInflater.inflate(R.layout.bottom_nav_check_connection,null)
            val btnRefreshLogin = view.findViewById<Button>(R.id.btnRefreshLogin)
            val btnQuiteLogin = view.findViewById<Button>(R.id.btnQuiteLogin)
            btnRefreshLogin.setOnClickListener {
                startActivity(Intent(this,Login::class.java))
                this.finish()
            }
            btnQuiteLogin.setOnClickListener { this.finish() }
            alertDialog.setView(view)
            alertDialog.setCancelable(false)
            alertDialog.create()
            alertDialog.show()
        }
    }
    private fun isConnected(): Boolean {
        var isConnect = false
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val connectionInfo = connectivityManager.activeNetworkInfo
        if (connectionInfo != null && connectionInfo.isConnected) isConnect = true
        return isConnect
    }
}