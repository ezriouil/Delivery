package www.ezriouil.delivery.deliveryMan.fragments.home.new_order

class OrderInfo{
    var id:String?=null
    var to:String?=null
    var name:String?=null
    var address:String?=null
    var price:Int = 0
    var quantity:Int=0
    var phone:String?=null
    var notes:String?=null
    var status:String?=null
    var isPaid:Boolean=false
    var timeSend:String?=null
    var timePaid:String?=null
    constructor()
    constructor(id:String, to:String, name:String,address:String,price:Int,quantity:Int,phone:String,notes:String, status:String, paid:Boolean, timeSend:String, timePaid:String){
        this.id = id
        this.to = to
        this.name = name
        this.address=address
        this.price=price
        this.quantity=quantity
        this.phone = phone
        this.notes=notes
        this.status = status
        this.isPaid = paid
        this.timeSend = timeSend
        this.timePaid = timePaid
    }
}