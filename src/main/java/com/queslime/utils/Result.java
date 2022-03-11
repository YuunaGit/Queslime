package com.queslime.utils;

import com.queslime.enums.Info;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Result {
    private Info info;
    private Object data;

    public Result(Object data) {
        this.data = data;
    }

    public Result(Info info, Object data) {
        this.info = info;
        this.data = data;
    }
}
