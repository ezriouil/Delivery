package www.ezriouil.delivery.statistics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import www.ezriouil.delivery.R
import www.ezriouil.delivery.databinding.ShowStatisticOfDeliveryManBinding
import www.ezriouil.delivery.statistics.fragments.all_orders.StatisticFragmentAllOrders
import www.ezriouil.delivery.statistics.fragments.orders_delivery_done.StatisticFragmentOrderDeliveryDone
import www.ezriouil.delivery.statistics.fragments.orders_no_answer.StatisticFragmentOrderNoAnswer
import www.ezriouil.delivery.statistics.fragments.orders_paid.StatisticFragmentOrderPaid
import www.ezriouil.delivery.statistics.fragments.orders_plus_tard.StatisticFragmentOrderPlusTard

class ShowStatisticOfDeliveryMan : AppCompatActivity(){
    private lateinit var binding:ShowStatisticOfDeliveryManBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ShowStatisticOfDeliveryManBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val uidAccount = intent.getStringExtra("3")
        replaceFragment(StatisticFragmentAllOrders(),"nav1",uidAccount.toString())
        binding.statisticBottomNavigationView.setItemSelected(R.id.statistic_1_nav,true)

        binding.statisticBottomNavigationView.setOnItemSelectedListener {
            when(it){
                R.id.statistic_1_nav -> { replaceFragment(StatisticFragmentAllOrders(),"nav1",uidAccount.toString()) }
                R.id.statistic_2_nav -> { replaceFragment(StatisticFragmentOrderNoAnswer(),"nav2",uidAccount.toString()) }
                R.id.statistic_3_nav -> { replaceFragment(StatisticFragmentOrderPaid(),"nav3",uidAccount.toString()) }
                R.id.statistic_4_nav -> { replaceFragment(StatisticFragmentOrderDeliveryDone(),"nav4",uidAccount.toString()) }
                R.id.statistic_5_nav -> { replaceFragment(StatisticFragmentOrderPlusTard(),"nav5",uidAccount.toString()) }
            } }
    }
    private fun replaceFragment(fragment: Fragment,bundleNameUid:String,uid:String){
        val bundle = Bundle()
        bundle.putString(bundleNameUid, uid)
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().apply {
            replace(binding.statisticFrameLayout.id,fragment)
            commit()
        }
    }
}