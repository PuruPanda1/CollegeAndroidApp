package com.purabmodi.devhacksapp.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class utils {

    fun dateFormatter(date:LocalDateTime):String{
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return date.format(formatter)
    }

    fun getUUID() = UUID.randomUUID().toString()

//    function to check for the application info
    fun isApplicationName(packageName:String,context:Context):Boolean{
    return try{
        context.packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA)
        true
    }catch (e:NameNotFoundException){
        false
    }

    return false
    }
}