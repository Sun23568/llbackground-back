package com.llback.common.constants;

/**
 * 公开标识枚举
 */
public enum PublicFlagEnum {
    NO_FLAG("0", "否"),
    YES_FLAG("1", "是");

    private final String code;
    private final String description;

    PublicFlagEnum(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public static PublicFlagEnum fromCode(String code) {
        for (PublicFlagEnum flag : values()) {
            if (flag.code.equals(code)) {
                return flag;
            }
        }
        throw new IllegalArgumentException("Invalid flag code: " + code);
    }
}
