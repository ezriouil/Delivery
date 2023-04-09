package www.ezriouil.delivery

import android.content.Context
import android.widget.Toast

object Constants {

    const val ADMIN_GMAIL ="admin@gmail.com"
    const val ADMIN_PASSWORD ="admin123"

    const val S_P_EMAIL = "email"
    const val S_P_PASSWORD = "password"
    const val S_P_LOGIN = "login"

    const val ORDER_STATUS_WAITING = "EN-COURS"
    const val ORDER_STATUS_CONFIRMED = "CONFIRMER"
    const val ORDER_STATUS_CANCELED = "REFUSER"
    const val ORDER_STATUS_ANNULER = "ANNULER"
    const val ORDER_STATUS_NO_ANSWER = "PAS DE REPONSE"
    const val ORDER_STATUS_PLUS_TARD= "PLUS-TARD"
    const val ORDER_STATUS_DONE = "LIVRE"
    const val ORDER_STATUS_PAID = "PAYÉ"

    const val ORDER_IS_PAID_TRUE:Boolean = true
    const val ADMIN ="ADMIN"
    const val DELIVERY_MEN_IMG ="DELIVERY_MAN_IMAGE"
    const val DELIVERY_MEN ="DELIVERY_MEN"
    const val ORDERS ="ORDERS"

     fun toast(context: Context,msg:String){ Toast.makeText(context,msg, Toast.LENGTH_LONG).show()}
}