package www.ezriouil.delivery.deliveryMan.fragments.home.history_delivery_man

class OrderInfoHistory{
    var name:String?=null
    var address:String?=null
    var price:Int = 0
    var timePaid:String?=null
    constructor()
    constructor(name:String,address:String,price:Int, timePaid:String){
        this.name = name
        this.address=address
        this.price=price
        this.timePaid = timePaid
    }
}