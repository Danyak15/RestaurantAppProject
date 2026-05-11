package com.example.restaurantapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val id: Long,
    val restaurantId: Int?,
    val title: String,
    val content: String,
    val createdAt: String
) : Parcelable
