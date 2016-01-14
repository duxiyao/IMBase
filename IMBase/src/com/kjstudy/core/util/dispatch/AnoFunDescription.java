package com.kjstudy.core.util.dispatch;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description 改函数处理逻辑描述
 * @author duxiyao
 * @date 2016年1月9日 上午10:41:20
 * 
 */ 
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AnoFunDescription {
    /**  
    * @Description:  描述改函数是做什么逻辑的
    * @author  duxiyao
    * @date 2016年1月13日 下午2:44:35 
    * @return      
    */
    public String description();
}
