package com.lishang.demo

import android.view.View
import com.lishang.aop.click.SingleClick



fun View.setOnSingleClick(action: (view: View) -> Unit) {

    setOnClickListener(
            @SingleClick
            fun(v: View) {
                action.invoke(v)
            }
    )
}