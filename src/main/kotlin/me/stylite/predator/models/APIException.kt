package me.stylite.predator.models

import com.google.gson.annotations.SerializedName

data class APIException(@SerializedName("Error") val message: String)
