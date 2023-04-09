package www.ezriouil.delivery.deliveryMan.fragments.home

class DeliveryMenInfo {
    var id:String?=null
    var uIdAccount:String?=null
    var img:String?=null
    var userName:String?=null
    var gmail:String?=null
    var password:String?=null
    var city:String?=null
    var street:String?=null
    var number:String?=null
    var timeRegistered:String?=null

    constructor()

    constructor(id:String,uIdAccount:String,img:String,userName:String,gmail:String,password:String,city:String,street:String,number:String,timeRegistered:String):this()
    {
        this.id=id
        this.uIdAccount=uIdAccount
        this.img=img
        this.userName=userName
        this.gmail=gmail
        this.password=password
        this.city=city
        this.street=street
        this.number=number
        this.timeRegistered=timeRegistered
    }

}