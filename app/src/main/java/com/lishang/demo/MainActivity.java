package com.lishang.demo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.lishang.aop.click.SingleClick;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    private Button btn1;
    private Button btn2;
    private Button btn3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();


        btn1.setOnClickListener(new View.OnClickListener() {
            @SingleClick(enabled = false)
            @Override
            public void onClick(View v) {
                log("@SingleClick(enabled = false)");
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @SingleClick(value = 2000)
            @Override
            public void onClick(View v) {
                log("@SingleClick(value = 2000)");

            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                log("  onClick");

            }
        });
    }


    private void initView() {
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
    }

    @SingleClick
    @OnClick(R.id.btn)
    public void onClickBtn() {

        startActivity(new Intent(this, Main2Activity.class));

    }

    @SingleClick(filter = {R.id.btn5})
    @OnClick({R.id.btn4, R.id.btn5, R.id.btn6})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn4:
                log("btn4 不能快速点击");
                break;
            case R.id.btn5:
                log("btn5 能快速点击");
                break;
            case R.id.btn6:
                log("btn6 不能快速点击");
                break;
        }
    }

    private void log(String msg) {
        Log.e("MainActivity", msg);
    }

    @SingleClick(filter = {R.id.btn8}, enabled = false)
    @OnClick({R.id.btn7, R.id.btn8, R.id.btn9})
    public void onViewClicked1(View view) {
        switch (view.getId()) {
            case R.id.btn7:
                log("btn7 能快速点击");
                break;
            case R.id.btn8:
                log("btn8 不能快速点击");
                break;
            case R.id.btn9:
                log("btn9 能快速点击");
                break;
        }
    }

}
