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
@TableName(schema = "queslime", value = "r_problem_tags")
public class ProblemWithTags {
    private Integer pid;
    private Integer tid;

    public ProblemWithTags(Integer pid, Integer tid) {
        this.pid = pid;
        this.tid = tid;
    }
}
