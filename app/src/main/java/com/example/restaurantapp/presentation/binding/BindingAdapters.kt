package com.example.restaurantapp.presentation.binding

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.restaurantapp.R

@BindingAdapter("imageUrl")
fun ImageView.loadImage(url: String?) {
    val fullUrl = if (url != null) "http://10.0.2.2:8080$url" else null

    load(fullUrl) {
        crossfade(true)
        placeholder(R.drawable.ic_restaurant_placeholder)
        error(R.drawable.ic_restaurant_placeholder)
        fallback(R.drawable.ic_restaurant_placeholder)
    }
}
