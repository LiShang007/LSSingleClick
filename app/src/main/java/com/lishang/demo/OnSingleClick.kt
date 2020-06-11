package com.lishang.demo

import android.view.View
import com.lishang.aop.click.SingleClick

fun View.setOnUnSingleClick(action: (view: View) -> Unit) {

    setOnClickListener(
            @SingleClick(enabled = false)
            fun(v: View) {
                action.invoke(v)
            }
    )
}

