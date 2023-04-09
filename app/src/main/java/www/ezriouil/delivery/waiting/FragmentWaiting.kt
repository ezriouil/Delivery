package www.ezriouil.delivery.waiting

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.FragmentActivity
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import www.ezriouil.delivery.*
import www.ezriouil.delivery.databinding.FragmentWaitingBinding
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

class  FragmentWaiting : Fragment() , AllListener{
    private lateinit var binding:FragmentWaitingBinding
    private lateinit var myNotification:Notification.Builder
    private val uidAccount = FirebaseAuth.getInstance().currentUser!!.uid
    private val data = mutableListOf<OrderInfo>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        binding = FragmentWaitingBinding.inflate(layoutInflater)
        val fireBase = Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount)
        fireBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()
                for (item in snapshot.children){
                    val itemData = item.getValue(OrderInfo::class.java)
                    if (itemData?.status == Constants.ORDER_STATUS_WAITING) {
                        data.add(itemData)
                        notification(itemData,binding.root.rootView.context)
                }
                val waitingOrdersRV = WaitingOrdersRV(binding.root.rootView.context,data.reversed(),this@FragmentWaiting)
                binding.waitingOrdersRv.adapter = waitingOrdersRV
                waitingOrdersRV.updateData(data)

                }
            }
            override fun onCancelled(error: DatabaseError) {}
        })

        return binding.root
    }
    fun notification(item: OrderInfo,myContext: Context) {
        myNotification = Notification.Builder(myContext, "my channel id")
        myNotification.apply {
            setSmallIcon(R.drawable.icon_add)
            setContentTitle(item.name)
            setContentText("Nouvelle commande")
            setColor(Color.GREEN)
            setSubText(item.timeSend)
        }.build()
        val notificationChannel = NotificationChannel("my channel id", "my channel name", NotificationManager.IMPORTANCE_DEFAULT)
        val notificationService = myContext.getSystemService(FragmentActivity.NOTIFICATION_SERVICE) as NotificationManager
        notificationService.createNotificationChannel(notificationChannel)
        notificationService.notify(item.phone!!.toInt(), myNotification.build())
    }

    override fun orderConfirm(orderInfo: OrderInfo) {}
    override fun orderRefuser(orderInfo: OrderInfo) {}
    override fun orderLiveryBtnPaid(orderInfo: OrderInfo) {}

    @SuppressLint("SimpleDateFormat")
    override fun orderWaitBtnConfirm(orderInfo: OrderInfo) {
        val timeNow = SimpleDateFormat("MM/dd HH:mm").format(Date())
        val alertDialog = AlertDialog.Builder(this@FragmentWaiting.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Confirmer"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount).child(orderInfo.id.toString()).setValue(
                OrderInfo(
                    orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_CONFIRMED,orderInfo.isPaid,orderInfo.timeSend!!,timeNow
                )
            ).addOnSuccessListener {
                Constants.toast(this@FragmentWaiting.requireContext(),"la commande à étè confirmer")
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    override fun orderWaitBtnAnnuler(orderInfo: OrderInfo) {
        val timeNow = SimpleDateFormat("MM/dd HH:mm").format(Date())
        val alertDialog = AlertDialog.Builder(this@FragmentWaiting.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Annuler La Commande"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount).child(orderInfo.id.toString()).setValue(
                OrderInfo(
                    orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_ANNULER,orderInfo.isPaid,orderInfo.timeSend!!,timeNow
                )
            ).addOnSuccessListener {
                Constants.toast(this@FragmentWaiting.requireContext(),"la commande à étè Annuler")
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }

    @SuppressLint("SimpleDateFormat")
    override fun orderWaitBtnNoAnswer(orderInfo: OrderInfo) {
        val timeNow = SimpleDateFormat("MM/dd HH:mm").format(Date())
        val alertDialog = AlertDialog.Builder(this@FragmentWaiting.requireContext())
        alertDialog.setTitle("êtes-vous sûr !?")
        alertDialog.setMessage("command : ${orderInfo.name}")
        alertDialog.setPositiveButton("Pas de réponse"){ _ , _ ->
            Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount).child(orderInfo.id.toString()).setValue(
                OrderInfo(
                    orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_NO_ANSWER,orderInfo.isPaid,orderInfo.timeSend!!,timeNow
                )
            ).addOnSuccessListener {
                Constants.toast(this@FragmentWaiting.requireContext(),"la commande à étè retournée à l'admin")
            }
        }
        alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
        alertDialog.show()
    }

    override fun orderWaitBtnCall(orderInfo: OrderInfo) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${orderInfo.phone}"))
        startActivity(intent)
    }

    @SuppressLint("MissingInflatedId", "InflateParams")
    override fun orderWaitBtnNotes(orderInfoNotes: String) {
        val sheetDialogNotes = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.bott_nav_waiting_notes,null)
        view.findViewById<AppCompatTextView>(R.id.bot_nav_wait_note_notes).text = orderInfoNotes
        sheetDialogNotes.setContentView(view)
        sheetDialogNotes.show()
    }

    @SuppressLint("MissingInflatedId")
    override fun orderWaitBtnPlusTard(orderInfo: OrderInfo) {
        val sheetDialog = BottomSheetDialog(requireContext())
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_nav_btn_plus_tard,view?.findViewById(R.id.bottom_sheet_design))
        view.findViewById<ImageView>(R.id.close_bottom_sheet).setOnClickListener {
            sheetDialog.dismiss()
        }
        val monthOfBottomSheet = view.findViewById<NumberPicker>(R.id.newMonth)
        val dayOfBottomSheet = view.findViewById<NumberPicker>(R.id.newDay)
        monthOfBottomSheet.apply {
            minValue = 1
            maxValue = 12
            value = LocalDateTime.now().monthValue
        }
        dayOfBottomSheet.apply {
            minValue = 1
            maxValue = 31
            value = LocalDateTime.now().dayOfMonth+1
        }
        view.findViewById<Button>(R.id.btn_ok).setOnClickListener {
            val alertDialog = AlertDialog.Builder(this@FragmentWaiting.requireContext())
            alertDialog.setTitle("êtes-vous sûr !?")
            alertDialog.setMessage("command : ${orderInfo.name}")
            alertDialog.setPositiveButton("Plus tard"){ _ , _ ->
                Firebase.database.getReference(Constants.ADMIN).child(Constants.ORDERS).child(uidAccount).child(orderInfo.id.toString()).setValue(
                    OrderInfo(
                        orderInfo.id!!,orderInfo.to!!,orderInfo.name!!,orderInfo.address!!,orderInfo.price,orderInfo.quantity,orderInfo.phone!!,orderInfo.notes!!,Constants.ORDER_STATUS_PLUS_TARD,orderInfo.isPaid,orderInfo.timeSend!!,"${monthOfBottomSheet.value}/${dayOfBottomSheet.value} 10:00"
                    )
                ).addOnSuccessListener {
                    Constants.toast(this@FragmentWaiting.requireContext(),"la commande à étè retournée à l'admin")
                    sheetDialog.dismiss()
                }
            }
            alertDialog.setNegativeButton("Annule"){_ , _ -> alertDialog.setOnDismissListener { it.dismiss() } }
            alertDialog.show()
        }
        sheetDialog.setCancelable(false)
        sheetDialog.setContentView(view)
        sheetDialog.show()
    }
}