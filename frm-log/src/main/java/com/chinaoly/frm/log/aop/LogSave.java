package com.chinaoly.frm.log.aop;

import java.lang.annotation.*;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogSave {
	String description() default "";
	String title() default "";
	methodType methodType() default methodType.SEARCH;

}
