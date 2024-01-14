package com.mahmutalperenunal.tmdbmovieapp.model

import com.google.gson.annotations.SerializedName

data class ProductionCompany(
    @SerializedName("name")
    val name: String?,
)