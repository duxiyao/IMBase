package com.kjstudy.core.util.dispatch;

import java.lang.reflect.Method;
import java.util.LinkedList;

/**
 * @Description 一个接一个的处理逻辑函数
 * @author duxiyao
 * @date 2016年1月9日 上午10:41:20
 * 
 */
public class DispatchByChain {
    private LinkedList<Method> mHandlers;

    public synchronized void dispatch(Object obj, Object... args) {
        if (mHandlers == null) {
            mHandlers = new LinkedList<Method>();
            for (Method m : obj.getClass().getMethods()) {
                AnoFunDescription ano = m
                        .getAnnotation(AnoFunDescription.class);
                if (ano != null) {
                    mHandlers.add(m);
                }
            }
        }

        for (Method m : mHandlers) {
            try {
                Class<?>[] pt = m.getParameterTypes();
                if (args == null || pt != null && pt.length == args.length)
                    m.invoke(obj, args);
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }
}
