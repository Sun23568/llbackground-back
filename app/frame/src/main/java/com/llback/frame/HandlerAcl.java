package com.llback.frame;

import java.lang.annotation.*;

/**
 * 处理器所需权限
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HandlerAcl {
    /**
     * 权限标识
     *
     * @author yz.sun
     * @date 2025/7/14
     */
    String value() default "";

    /**
     * 拥有任意一个权限即可
     *
     * @author yz.sun
     * @date 2025/7/14
     */
    String[] hasAny() default {};
}
