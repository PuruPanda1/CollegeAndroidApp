package com.purabmodi.devhacksapp.data.models

import java.time.LocalDateTime

data class Item(
    val uid: String?="",
    val name: String?="",
    val category: String?="",
    val description: String?="",
    val verified: Boolean?=false,
    val submitDate: String?="",
    val outDate: String?="",
    val submitStudentName: String?="",
    val receiveStudentName: String?="",
    val image: String?=""
)
