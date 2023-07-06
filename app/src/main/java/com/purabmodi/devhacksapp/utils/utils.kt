package com.purabmodi.devhacksapp.utils

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID

class utils {

    fun dateFormatter(date:LocalDateTime):String{
        val formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")
        return date.format(formatter)
    }

    fun getUUID() = UUID.randomUUID().toString()
}