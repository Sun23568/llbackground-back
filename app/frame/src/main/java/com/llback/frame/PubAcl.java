package com.llback.frame;

import java.lang.annotation.*;

/**
 * 公开权限
 * 标注在处理器上，表示该处理器不需要登录权限
 *
 * @author yz.sun
 * @date 2025/7/14
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PubAcl {
}
