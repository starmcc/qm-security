package com.starmcc.qmsecurity.note;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author qm
 * @date 2018/12/22 21:55
 * @Description 忽略安全认证、授权的注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface QmAuthPass {
}
