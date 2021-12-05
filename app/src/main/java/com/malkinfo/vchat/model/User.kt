package com.malkinfo.vchat.model

//Data class to get User information from Firebase
class User {
    var uid:String? = null
    var intrst:String? = null
    var name:String? = null
    var phoneNumber:String? = null
    var profileImage:String? = null
    constructor(){}
    constructor(
        uid:String?,
        intrst:String?,
        name:String?,
        phoneNumber:String?,
        profileImage:String?

    ){
        this.uid = uid
        this.intrst = intrst
        this.name = name
        this.phoneNumber = phoneNumber
        this.profileImage = profileImage

    }

}