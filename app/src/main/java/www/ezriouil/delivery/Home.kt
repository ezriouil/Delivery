package www.ezriouil.delivery

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView
import www.ezriouil.delivery.annuler.FragmentAnnuler
import www.ezriouil.delivery.cancel.FragmentCancel
import www.ezriouil.delivery.confirmer.FragmentConfirmed
import www.ezriouil.delivery.databinding.HomeBinding
import www.ezriouil.delivery.livrey.FragmentLivery
import www.ezriouil.delivery.paid.FragmentPaid
import www.ezriouil.delivery.waiting.FragmentWaiting

class Home : AppCompatActivity(){

    private lateinit var binding: HomeBinding
    private var isAdded = false
    private var fragmentAnnuler:FragmentAnnuler?=null
    private var fragmentLivery:FragmentLivery?=null
    private var fragmentCancel:FragmentCancel?=null
    private var fragmentConfirmed:FragmentConfirmed?=null
    private var fragmentPaid:FragmentPaid?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = HomeBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.myToolBar)
        drawerHeaderInit()
        initView()
    }
    @SuppressLint("RestrictedApi")
    private fun initView() {
        val fragmentWaiting=FragmentWaiting()
        val actionBarDrawerToggle = ActionBarDrawerToggle(
            this,
            binding.myDrawerLayout,
            binding.myToolBar,
            R.string.open,
            R.string.close
        )
        binding.myDrawerLayout.addDrawerListener(actionBarDrawerToggle)
        actionBarDrawerToggle.syncState()
        binding.myNavigationView.setCheckedItem(R.id.icon_waiting)
        replaceFragment(fragmentWaiting)
        binding.myNavigationView.setNavigationItemSelectedListener { eachItem ->
            when (eachItem.itemId) {
                R.id.icon_waiting -> {
                    replaceFragment(fragmentWaiting)
                    isAdded = true
                }
                R.id.icon_confirme -> {
                    if (fragmentConfirmed == null) fragmentConfirmed = FragmentConfirmed()
                    replaceFragment(fragmentConfirmed)
                    isAdded = false
                }
                R.id.icon_cancel -> {
                    if (fragmentCancel == null) fragmentCancel = FragmentCancel()
                    replaceFragment(fragmentCancel)
                    isAdded = false
                }
                R.id.icon_livrey -> {
                    if (fragmentLivery == null) fragmentLivery = FragmentLivery()

                    replaceFragment(fragmentLivery)
                    isAdded = false
                }
                R.id.icon_paid -> {
                    if (fragmentPaid == null) fragmentPaid = FragmentPaid()
                    replaceFragment(fragmentPaid)
                    isAdded = false
                }
                R.id.icon_Annuler -> {
                    if (fragmentAnnuler == null) fragmentAnnuler = FragmentAnnuler()
                    replaceFragment(fragmentAnnuler)
                    isAdded = false
                }
                R.id.icon_close -> {
                    val alertDialog = AlertDialog.Builder(this@Home)
                    alertDialog.setTitle("êtes-vous sûr !?")
                    alertDialog.setMessage("Exit")
                    alertDialog.setPositiveButton("Oui") { _, _ -> this.finish() }
                    alertDialog.setNegativeButton("Non") { _, _ -> alertDialog.setOnDismissListener { it.dismiss() } }
                    alertDialog.show()
                }
            }
            binding.myDrawerLayout.closeDrawer(GravityCompat.START)
            return@setNavigationItemSelectedListener true
        }
    }
    private fun drawerHeaderInit() {
        var imgData: String? = null
        var nameData: String? = null
        val uid = FirebaseAuth.getInstance().currentUser!!.uid
        Firebase.database.getReference(Constants.ADMIN).child(Constants.DELIVERY_MEN)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        val itemData = it.getValue(DeliveryMenInfo::class.java)
                        if (itemData!!.uIdAccount == uid) {
                            imgData = itemData.img
                            nameData = itemData.userName
                        }
                    }
                    binding.myNavigationView.getHeaderView(0).apply {
                        findViewById<AppCompatTextView>(R.id.header_drawer_name).text = nameData
                        Picasso.get().load(imgData).apply {
                            placeholder(R.drawable.login)
                            into(findViewById<CircleImageView>(R.id.header_drawer_img))
                            centerCrop()
                        }
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }
    private fun replaceFragment(fragment: Fragment?){
        supportFragmentManager.beginTransaction().apply {
            replace(binding.frameLayout.id,fragment!!)
            commit()
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        if (binding.myDrawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.myDrawerLayout.closeDrawer(GravityCompat.START)
        } else
            if (isAdded){
                val alertDialog = AlertDialog.Builder(this@Home)
                alertDialog.setTitle("êtes-vous sûr !?")
                alertDialog.setMessage("Exit")
                alertDialog.setPositiveButton("Oui"){ _ , _ -> this.finish() }
                alertDialog.setNegativeButton("Non"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
                alertDialog.show()
            }
            else{
                replaceFragment(FragmentWaiting())
                isAdded = true
            }
    }


}