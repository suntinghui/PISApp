package com.lkpower.base.widgets

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class RecyclerItemDecoration(val itemTBSpace: Int, val itemLRSapce:Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)

        outRect?.bottom = itemTBSpace
        outRect?.left = itemLRSapce
        outRect?.right = itemLRSapce

    }


}