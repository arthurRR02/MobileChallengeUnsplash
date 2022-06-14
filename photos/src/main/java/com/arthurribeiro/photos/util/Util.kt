package com.arthurribeiro.photos.util

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.AbsListView
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import java.io.InputStream
import java.net.URL


fun View.getImageFromUrl(activity: Activity, url: String, onResReadyCallback: (Drawable) -> Unit) {
    Glide.with(activity.baseContext)
        .asBitmap()
        .load(url)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                val drawable: Drawable = BitmapDrawable(resources, resource)
                onResReadyCallback.invoke(drawable)
            }

            override fun onLoadCleared(placeholder: Drawable?) {}

        })
}

