package www.ezriouil.delivery.deliveryMan.fragments.home

interface AllListener{
    fun sendOrder(deliveryMenInfo: DeliveryMenInfo)
    fun history(deliveryMenUid: String)
    fun showAllInfo(deliveryMenInfo: DeliveryMenInfo)
}