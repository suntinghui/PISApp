package com.lkpower.base.widgets

import android.content.Context
import android.util.AttributeSet
import android.widget.ImageView

class SquareImageView : ImageView {

    constructor(ctx:Context) : super(ctx)

    constructor(ctx:Context, atts: AttributeSet) : super(ctx, atts)

    constructor(ctx:Context, atts: AttributeSet, defStyleAttr:Int) : super(ctx, atts, defStyleAttr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, widthMeasureSpec)
    }
}