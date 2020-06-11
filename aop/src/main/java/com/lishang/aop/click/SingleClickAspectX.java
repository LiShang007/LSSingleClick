package com.lishang.aop.click;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * author:LiShang
 * date:  2020-06-11
 * desc:  AOP拦截点击事件，防止重复点击
 */
@Aspect
public class SingleClickAspectX {

    /**
     * 默认点击时间间隔
     */
    private static final long DEFAULT_CLICK_INTERVAL = 1500;
    /**
     * 点击间隔
     */
    private long CLICK_INTERVAL = DEFAULT_CLICK_INTERVAL;

    /**
     * 最后一次点击的时间
     */
    private long mLastClickTime;
    /**
     * 最后一次点击的View id
     */
    private long mLastClickViewId;


    @Pointcut("onSingleClickMethod()")
    public void onClickMethodAop(ProceedingJoinPoint joinPoint) {
    }

    /**
     * 切面方法
     * 拦截带有注解 {@SingleClick}的方法
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Around("execution (@com.lishang.aop.click.SingleClick * *(..))")
    public void onSingleClickMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        long interval = CLICK_INTERVAL;
        boolean enabled = true;
        List<Integer> filter = new ArrayList<>();
        //获取注解方法
        if (joinPoint.getSignature() != null) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            if (method != null) {
                if (method.isAnnotationPresent(SingleClick.class)) {

                    SingleClick singleClick = method.getAnnotation(SingleClick.class);
                    if (singleClick != null) {
                        interval = singleClick.value();
                        enabled = singleClick.enabled();
                        int[] ids = singleClick.filter();
                        for (int id : ids) {
                            filter.add(id);
                        }
                    }
                }
            }
        }


        View view = findView(joinPoint);

        if (view == null) {
            joinPoint.proceed();
        } else {
            //检测快速点击
            boolean check = enabled;
            if (filter.contains(view.getId())) {

                if (enabled) {
                    //true View 可以重复点击
                    check = false;
                } else {
                    //false View 不可重复点击
                    check = true;
                }
            }


            if (check) {
                //check true 检测点击间隔是否超过规定间隔

                if (!isQuickDoubleClick(view, interval)) {
                    Object obj = joinPoint.proceed();
                    if (obj != null) {
                        Log.e("proceed", "obj:" + obj.toString());
                    }
                }
            } else {
                joinPoint.proceed();
            }

        }
    }

    /**
     * 查找View
     *
     * @param joinPoint
     * @return
     */
    private View findView(JoinPoint joinPoint) {
        Object[] obj = joinPoint.getArgs();
        if (obj != null && obj.length > 0) {

            for (Object o : obj) {
                if (o instanceof View) {
                    return (View) o;
                }
            }

        }
        return null;
    }

    /**
     * 是否是快速点击
     *
     * @param view
     * @param click_interval
     * @return
     */
    private boolean isQuickDoubleClick(View view, long click_interval) {

        long viewId = view.getId();

        long currentTime = System.currentTimeMillis();

        long interval = Math.abs(currentTime - mLastClickTime);
        if (interval >= click_interval) {
            //点击间隔 大于 设定的时间差,可以再次点击
            mLastClickTime = currentTime;
            mLastClickViewId = viewId;
            return false;
        } else if (viewId != mLastClickViewId) {
            //点击间隔小于时间差 但是不是同一个按钮,可以点击
            mLastClickTime = currentTime;
            mLastClickViewId = viewId;
            return false;
        } else {
            return true;
        }

    }


}
