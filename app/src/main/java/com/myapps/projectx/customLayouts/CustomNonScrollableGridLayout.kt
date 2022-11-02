package com.myapps.projectx.customLayouts

import android.content.Context
import androidx.recyclerview.widget.GridLayoutManager

class CustomNonScrollableGridLayout(context: Context, spanCount: Int): GridLayoutManager(context, spanCount) {
    override fun canScrollVertically(): Boolean {
        return false
    }
}