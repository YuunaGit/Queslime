package com.queslime.utils;

import com.queslime.enums.Info;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Result {
    private Info info;
    private Object data;

    public Result() {}

    public Result info(Info info) {
        this.info = info;
        return this;
    }

    public Result info(Info info, Object data) {
        this.info = info;
        this.data = data;
        return this;
    }
}
