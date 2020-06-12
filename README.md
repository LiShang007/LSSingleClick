# LSSingleClick 
>一行代码实现防止View快速点击，支持Java和Kotlin

###  引用

1.在项目````根````目录下的````build.gradle````加入以下代码
    
    buildscript {
    
        dependencies {
            ......

            classpath 'com.hujiangaspectjx:gradle-android-plugin-aspectjx:2.0.10'

        }
    }


2.在````app Module````下的 `build.gradle`加入以下代码 最新版本[ ![Download](https://api.bintray.com/packages/lishang007/maven/LSSingleClick/images/download.svg?version=1.0.0) ](https://bintray.com/lishang007/maven/LSSingleClick/1.0.0/link)


        apply plugin: 'android-aspectjx'

        android{
            ......
        }

        dependencies{
            ....
            
            implementation 'com.lishang.aop.click:LSSingleClick:1.0.0'
        }

### 使用

>在点击方法上面添加注解 ````@SingleClick````,就可以防止按钮重复点击。默认低于1500ms，进行拦截。

#### 注解 `SingleClick` 介绍

     /**
     * 默认点击时间间隔
     *
     * @return
     */
    long value() default 1500;

    /**
     * 是否防止重复点击
     *
     * @return
     */
    boolean enabled() default true;

    /**
     * 过滤对应的View
     * enabled ：true  过滤的是可以重复点击的View
     * enabled : false 过滤的是不可重复点击的View
     * 一句话，filter里面的View 逻辑是与enable 相反的
     *
     * @return
     */
    int[] filter() default {};

#### 例子

在````Java````中使用   

`默认拦截`，两次点击低于1500ms，进行拦截

    btn3.setOnClickListener(new View.OnClickListener() {
            @SingleClick
            @Override
            public void onClick(View v) {
                log("  onClick");

            }
    });

自定义拦截时间,`value=2000`,两次点击低于1500ms，进行拦截

    btn2.setOnClickListener(new View.OnClickListener() {
            @SingleClick(value = 2000)
            @Override
            public void onClick(View v) {
                log("@SingleClick(value = 2000)");

            }
    });

取消拦截 `enabled = false` 效果和不添加注解时一样

     btn1.setOnClickListener(new View.OnClickListener() {
            @SingleClick(enabled = false)
            @Override
            public void onClick(View v) {
                log("@SingleClick(enabled = false)");
            }
    });

过滤View点击  `filter = {R.id.btn5}`
>有时多个View调用同一个方法，而想对某个View，不拦截时使用,`filter`要和`enabled`结合使用。`filter+enabled=true`时，表示过滤View点击时`不拦截`，`filter+enabled=false`时，表示过滤View点击时`进行拦截`

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

在 `Kotlin`中使用,列了部分使用
    
    btn1.setOnClickListener(this)

    @SingleClick
    override fun onClick(v: View?) {
        log("click btn1")
    }

或者

    btn4.setOnClickListener(
        @SingleClick(value = 2000L)
        fun(_: View) {
            log("click btn4")
    })

还可以利用Kotlin特性对View添加扩展函数

    fun View.setOnSingleClick(action: (view: View) -> Unit) {

    setOnClickListener(
            @SingleClick
            fun(v: View) {
                action.invoke(v)
            }
        )
    }


    btn3.setOnSingleClick {
            log("click btn3")
    }