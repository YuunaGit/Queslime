package com.queslime.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@TableName(schema = "queslime", value = "e_problem")
public class Problem {
    @TableId(type = IdType.AUTO)
    private Integer pid;
    private Integer uid;
    private String problemContent;

    public Problem(Integer uid, String problemContent) {
        this.uid = uid;
        this.problemContent = problemContent;
    }
}