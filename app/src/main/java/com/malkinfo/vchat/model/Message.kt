package com.malkinfo.vchat.model

 //Data class to get msg information from Firebase
class Message {
    var personname: String? = null
    var messageId :String? =null
    var message :String? =null
    var senderId :String? =null
    var imageUrl :String? =null
    var timeStamp :Long =0
    constructor(){}
    constructor(
            personname: String?,
            message :String?,
            senderId :String?,
            timeStamp :Long

    ){
        this.personname = personname
        this.message = message
        this.senderId = senderId
        this.timeStamp = timeStamp

    }
}