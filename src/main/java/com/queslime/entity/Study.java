package com.queslime.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@TableName(schema = "queslime", value = "e_study")
public class Study {
    private Integer uid;
    private Integer pid;
    private Integer pass;
    
    public Study(Integer uid, Integer pid, Integer pass) {
        this.uid = uid;
        this.pid = pid;
        this.pass = pass;
    }
}
