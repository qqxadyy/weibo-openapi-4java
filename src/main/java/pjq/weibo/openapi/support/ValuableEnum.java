package pjq.weibo.openapi.support;

import java.lang.reflect.Field;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.utils.CheckUtils;

/**
 * <p>
 * -用于给自定义的枚举类增加value方法
 * <p>
 * Create at 2018年11月17日
 * 
 * @author pengjianqiang
 */
public interface ValuableEnum {
    /**
     * -1.如果枚举类有value属性，则value属性值作为枚举值<br/>
     * -2.否则使用ordinal值作为枚举值<br/>
     * -3.或枚举类重写value方法，用其返回值作为枚举值
     * 
     * @return
     */
    default String value() {
        Class<?> thisClass = getClass();
        CheckUtils.checkNotFalse(thisClass.isEnum(), "只有枚举类才能实现" + ValuableEnum.class.getSimpleName() + "接口");

        String enumValue = null;
        try {
            // 如果枚举类有value属性，则使用该属性值作为枚举的值
            Field valueField = thisClass.getDeclaredField("value");
            valueField.setAccessible(true);
            if (CheckUtils.isNotNull(valueField)) {
                enumValue = (String)valueField.get(this);
            }
        } catch (Exception e) {
        }

        if (CheckUtils.isEmpty(enumValue)) {
            // 没有value属性或获取属性值失败，则用ordinal方法
            enumValue = String.valueOf(((Enum<?>)this).ordinal());
        }
        return enumValue;
    }

    default String desc() {
        Class<?> thisClass = getClass();
        CheckUtils.checkNotFalse(thisClass.isEnum(), "只有枚举类才能实现" + ValuableEnum.class.getSimpleName() + "接口");

        String enumDesc = null;
        try {
            // 如果枚举类有desc属性，则使用该属性值作为枚举的值
            Field valueField = thisClass.getDeclaredField("desc");
            valueField.setAccessible(true);
            if (CheckUtils.isNotNull(valueField)) {
                enumDesc = (String)valueField.get(this);
            }
        } catch (Exception e) {
        }

        if (CheckUtils.isEmpty(enumDesc)) {
            // 没有value属性或获取属性值失败，则用ordinal方法
            enumDesc = String.valueOf(((Enum<?>)this).ordinal());
        }
        return enumDesc;
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    final class DefualtValueableEnum implements ValuableEnum {
        @SuppressWarnings("unchecked")
        public static <T extends Enum<T>> T valueOrNameOf(String enumTypeName, String valueOrName) {
            if (CheckUtils.isEmpty(enumTypeName)) {
                return null;
            }

            try {
                return valueOrNameOf((Class<T>)Class.forName(enumTypeName), valueOrName);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        public static <T extends Enum<T>> T valueOrNameOf(Class<T> enumType, String valueOrName) {
            CheckUtils.checkNotFalse(enumType.isEnum(), "只有枚举类才能实现" + ValuableEnum.class.getSimpleName() + "接口");

            T targetEnum = null;
            try {
                targetEnum = Enum.valueOf(enumType, valueOrName);
            } catch (Exception e) {
                // 根据枚举名没找到，则尝试使用value查找
                targetEnum = null;
                if (ValuableEnum.class.isAssignableFrom(enumType)) {
                    T[] enums = enumType.getEnumConstants();
                    for (T tempEnum : enums) {
                        if (valueOrName.equals(((ValuableEnum)tempEnum).value())) {
                            targetEnum = tempEnum;
                        }
                    }
                }
            }
            return targetEnum;
        }
    }
}