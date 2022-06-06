package com.queslime.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Getter
@Setter
@ToString
@NoArgsConstructor
@TableName(schema = "queslime", value = "e_study")
public class Study {
    @TableId(type = IdType.AUTO)
    private Integer studyId;
    private Integer uid;
    private Integer pid;
    private Integer pass;
    private Integer difficulty;
    private Timestamp createdAt;

    public Study(Integer uid, Integer pid, Integer pass, Integer difficulty) {
        this.uid = uid;
        this.pid = pid;
        this.pass = pass;
        this.difficulty = difficulty;
    }
}
