package com.purabmodi.devhacksapp.utils

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.PackageManager.NameNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class utils {

    val packages=HashMap<String,String>()
    val images=HashMap<String,String>()

    fun checkPackages(packageName: String):String{
        packages["com.snapchat.android"]="Snapchat"
        packages["com.instagram.android"]="Instagram"
        packages["com.spotify.music"]="Spotify"
        packages["com.facebook.lite"]="Facebook Lite"
        packages["cn.wps.moffice_eng"]="WPS Office"
        packages["com.linkedin.android"]="Linkedin"
        packages["com.github.android"]="Github"
        if(packages.contains(packageName)){
            return packages[packageName]!!
        }
        return ""
    }

    fun getImages(packageName: String):String{
        packages["com.snapchat.android"]="https://play-lh.googleusercontent.com/KxeSAjPTKliCErbivNiXrd6cTwfbqUJcbSRPe_IBVK_YmwckfMRS1VIHz-5cgT09yMo=w240-h480"
        packages["com.instagram.android"]="https://play-lh.googleusercontent.com/VRMWkE5p3CkWhJs6nv-9ZsLAs1QOg5ob1_3qg-rckwYW7yp1fMrYZqnEFpk0IoVP4LM=w240-h480"
        packages["com.spotify.music"]="https://play-lh.googleusercontent.com/cShys-AmJ93dB0SV8kE6Fl5eSaf4-qMMZdwEDKI5VEmKAXfzOqbiaeAsqqrEBCTdIEs=w240-h480"
        packages["com.facebook.lite"]="https://play-lh.googleusercontent.com/5pZMqQYClc5McEjaISPkvhF8pDmlbLqraTMwk1eeqTlnUSjVxPCq-MItIrJPJGe7xW4=w240-h480"
        packages["cn.wps.moffice_eng"]="https://play-lh.googleusercontent.com/DUohbTj-FKR_48Dav1c-1QZTSo6D0CzVNSO28RYhC2AH8_3B93AO3lTF3S2PKPQHeQY=s48"
        packages["com.linkedin.android"]="https://play-lh.googleusercontent.com/kMofEFLjobZy_bCuaiDogzBcUT-dz3BBbOrIEjJ-hqOabjK8ieuevGe6wlTD15QzOqw=w240-h480"
        packages["com.github.android"]="https://play-lh.googleusercontent.com/PCpXdqvUWfCW1mXhH1Y_98yBpgsWxuTSTofy3NGMo9yBTATDyzVkqU580bfSln50bFU=w240-h480"
        packages["com.whatsapp"]="https://play-lh.googleusercontent.com/bYtqbOcTYOlgc6gqZ2rwb8lptHuwlNE75zYJu6Bn076-hTmvd96HH-6v7S0YUAAJXoJN=w240-h480"
        packages["com.android.youtube"]="https://play-lh.googleusercontent.com/lMoItBgdPPVDJsNOVtP26EKHePkwBg-PkuY9NOrc-fumRtTFP4XhpUNk_22syN4Datc=s48"
        if(packages.contains(packageName)){
            return packages[packageName]!!
        }
        return ""
    }

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