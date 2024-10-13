package com.khoirulfahmi.kotlinportfolio

data class HadithResponse(
    val code: Int,
    val message: String,
    val data: HadithData,
    val error: Boolean
)

data class HadithData(
    val name: String,
    val id: String,
    val available: Int,
    val contents: HadithContent
)

data class HadithContent(
    val number: Int,
    val arab: String,
    val id: String
)