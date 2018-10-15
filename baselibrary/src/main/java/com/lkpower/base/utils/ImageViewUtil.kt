package com.lkpower.base.utils

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.widget.ImageView

object ImageViewUtil {

    fun releaseImageResource(imageView:ImageView) {
        if (imageView == null)
            return

        var drawable:Drawable = imageView.drawable
        if (drawable != null && drawable is BitmapDrawable) {
            var bitmapDrawable = drawable as BitmapDrawable
            var bitmap:Bitmap = bitmapDrawable.bitmap
            if (bitmap != null && !bitmap.isRecycled) {
                bitmap.recycle()
            }
        }
    }


}