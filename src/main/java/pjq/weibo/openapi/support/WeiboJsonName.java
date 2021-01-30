package pjq.weibo.openapi.support;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * 定义微博API返回的json字段名<br/>
 * 1.在类定义中使用注解即可，表示整个类的private属性都从json串中转换获取，且默认转换为String类型的值(只处理private属性)<br/>
 * 2.对于需要特殊定义json字段名，在需要转换的属性中再单独使用属性
 * 
 * @author pengjianqiang
 * @date 2021年1月20日
 */
@Retention(RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface WeiboJsonName {
    /**
     * json字段名，为空时用field的名
     * 
     * @return
     */
    String value() default "";

    /**
     * 默认true，如果有不需要从json中转换获取的属性值，则设为false
     * 
     * @return
     */
    boolean fromJson() default true;

    /**
     * 表示是否新版接口新增的返回(仅作标识用)
     * 
     * @return
     */
    boolean isNew() default false;

    /**
     * 表示是否新版接口新增的返回，且官网上没有相关描述(仅作标识用)
     * 
     * @return
     */
    boolean isNewAndNoDesc() default false;

    /**
     * 表示是否新版接口已废弃的返回(仅作标识用)
     * 
     * @return
     */
    boolean isDeleted() default false;
}