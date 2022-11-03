package com.example.hnbcurrencyapp.extensions

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

//ViewExtensions
inline fun CircleImageView.loadImage(res: Int) {
    Glide.with(this).load(res).into(this)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun View.isVisible() = visibility == View.VISIBLE

fun View.gone() {
    visibility = View.GONE
}
inline fun View.onClick(crossinline onClick: () -> Unit) {
    this.setOnClickListener {
        onClick()
    }
}

//AndroidExtensions
inline fun <T> LiveData<T>.subscribe(owner: LifecycleOwner, crossinline onChange: (T) -> Unit) =
    observe(owner, Observer {
        it?.run { onChange(this) }
    })
