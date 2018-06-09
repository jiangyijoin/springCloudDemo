package com.chinaoly.frm.core.aop;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.chinaoly.frm.core.entity.ResultCode;

/**
 * 方法说明注解
 * @className Explain
 * @author jiangyi
 * @date 2018年6月5日 下午6:51:16
 * @version 1.0.0
 */
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Explain {

    String value();

    int exceptionCode() default 0;

    ResultCode systemCode() default ResultCode.NOTHING;

}
