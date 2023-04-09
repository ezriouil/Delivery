package www.ezriouil.delivery.statistics

import www.ezriouil.delivery.deliveryMan.fragments.home.new_order.OrderInfo

interface AllListenerOFStatistics {
    fun show(deliveryMenInfoUidAccount: String)
    fun showAllOrdersRefuser(deliveryMenInfoUidAccount: String)
    fun orderNoAnswerReSend(orderInfo: OrderInfo)
    fun orderNoAnswerCancel(orderInfo: OrderInfo)
    fun orderAppel(number: String)
    fun orderPlusTardReSend(orderInfo: OrderInfo)
}