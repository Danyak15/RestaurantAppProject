package com.example.restaurantapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val id: Long,
    val restaurantId: Long?,
    val title: String,
    val content: String,
    val createdAt: String
) : Parcelable
