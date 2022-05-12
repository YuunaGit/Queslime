package com.queslime.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;
import java.util.Objects;

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
    private Integer difficulty;
    private Timestamp createdAt;
    private Integer bestSid;
    private Integer viewCount;
    private Integer solutionCount;

    public Problem(Integer uid, String problemContent, Integer difficulty) {
        this.uid = uid;
        this.problemContent = problemContent;
        this.difficulty = difficulty;
        this.bestSid = 0;
        this.viewCount = 0;
        this.solutionCount = 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Problem problem = (Problem) o;
        return pid.equals(problem.pid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pid);
    }
}