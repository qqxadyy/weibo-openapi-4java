package pjq.weibo.openapi.constant;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.EnhanceEnum;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BizConstant {
    // ----------------通用枚举----------------
    /**
     * 是或否类型--<br>
     * NO--N：否<br>
     * YES--Y：是
     */
    @AllArgsConstructor
    public static enum YesOrNo implements EnhanceEnum {
        NO("N"), YES("Y");

        @SuppressWarnings("unused")
        private String value;
    }

    /**
     * 状态类型--<br>
     * INVALID--0：无效<br>
     * VALID--1：有效
     */
    public static enum StatusType implements EnhanceEnum {
        INVALID, VALID
    }

    /**
     * ture或false类型
     */
    @AllArgsConstructor
    public static enum TrueOrFalse implements EnhanceEnum {
        FALSE("false"), TRUE("true");

        @SuppressWarnings("unused")
        private String value;
    }
}