package pjq.weibo.openapi.constant;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import pjq.weibo.openapi.support.ValuableEnum;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BizConstant {
    // ----------------通用枚举----------------
    /**
     * 是或否类型--<br/>
     * NO--N：否<br/>
     * YES--Y：是
     */
    public static enum YesOrNo implements ValuableEnum {
        NO("N"), YES("Y");

        @SuppressWarnings("unused")
        private String value;

        YesOrNo(String value) {
            this.value = value;
        }
    }

    /**
     * 状态类型--<br/>
     * INVALID--0：无效<br/>
     * VALID--1：有效
     */
    public static enum StatusType implements ValuableEnum {
        INVALID, VALID
    }

    /**
     * ture或false类型
     */
    public static enum TrueOrFalse implements ValuableEnum {
        FALSE("false"), TRUE("true");

        @SuppressWarnings("unused")
        private String value;

        TrueOrFalse(String value) {
            this.value = value;
        }
    }
}