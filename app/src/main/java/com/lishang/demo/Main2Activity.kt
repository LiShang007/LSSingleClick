package com.lishang.demo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.lishang.aop.click.SingleClick
import kotlinx.android.synthetic.main.activity_main2.*

class Main2Activity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


        btn1.setOnClickListener(this)

        btn2.setOnClickListener(@SingleClick
        fun(_: View) {
            log("click btn2")
        })


        btn3.setOnClickListener(
                @SingleClick(enabled = false)
                fun(_: View?) {
                    log("click btn3")

                }
        )

        btn3.setOnUnSingleClick {
            log("click btn3")
        }


        btn4.setOnClickListener(@SingleClick(value = 2000L)
        fun(_: View) {
            log("click btn4")
        })
    }

    private fun log(msg: String) {
        Log.e("MainActivity2", msg)
    }

    override fun onClick(v: View?) {
        log("click btn1")
    }

}
