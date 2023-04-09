package www.ezriouil.delivery


interface AllListener {
    fun orderConfirm(orderInfo: OrderInfo)
    fun orderRefuser(orderInfo: OrderInfo)
    fun orderLiveryBtnPaid(orderInfo: OrderInfo)
    fun orderWaitBtnConfirm(orderInfo: OrderInfo)
    fun orderWaitBtnAnnuler(orderInfo: OrderInfo)
    fun orderWaitBtnNoAnswer(orderInfo: OrderInfo)
    fun orderWaitBtnCall(orderInfo: OrderInfo)
    fun orderWaitBtnNotes(orderInfoNotes: String)
    fun orderWaitBtnPlusTard(orderInfo: OrderInfo)
}