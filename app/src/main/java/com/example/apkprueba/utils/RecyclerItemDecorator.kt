package com.example.apkprueba.utils

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class RecyclerItemDecorator(
    private var topSpacing: Int,
    private var rightSpacing: Int,
    private var bottomSpacing: Int,
    private var leftSpacing: Int
) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = bottomSpacing
        outRect.left = leftSpacing
        outRect.right = rightSpacing
        outRect.top = topSpacing
    }

}