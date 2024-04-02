package com.example.dnd.api

data class ResponseData (
    val message: String,
    val username: String
)

data class ErrorResponse(
    val error: String
)