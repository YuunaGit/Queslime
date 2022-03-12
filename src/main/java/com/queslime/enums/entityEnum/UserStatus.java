package com.queslime.enums.entityEnum;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL(0),
    NOT_ACTIVATED(1),
    RESTRICTED(2),
    BANNED(3),
    LOGOFF(4);

    @EnumValue
    private final byte value;

    UserStatus(int value) {
        this.value = (byte)value;
    }
}
