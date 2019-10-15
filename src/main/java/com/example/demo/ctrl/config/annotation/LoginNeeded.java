package com.example.demo.ctrl.config.annotation;

import java.lang.annotation.*;

/**
 * 标识一个方法的调用需要进行登录检查，没有实际作用，只是作为方法的标识条件触发登录检查
 *  https://blog.csdn.net/liang100k/article/details/79515910
 * @Target说明了Annotation所修饰的对象范围：Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、方法参数和本地变量（如循环变量、catch参数）。在Annotation类型的声明中使用了target可更加明晰其修饰的目标。
 * RetentionPolicy这个枚举类型的常量描述保留注释的各种策略，它们与元注释(@Retention)一起指定注释要保留多长时间
 * Documented注解表明这个注释是由 javadoc记录的，在默认情况下也有类似的记录工具。 如果一个类型声明被注释了文档化，它的注释成为公共API的一部分。
 */
@Documented
@Target({ElementType.METHOD})   //注明在方法上是使用
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginNeeded {
}
