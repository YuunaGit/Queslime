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
@TableName(schema = "queslime", value = "e_solution")
public class Solution {
    @TableId(type = IdType.AUTO)
    private Integer sid;
    private Integer pid;
    private Integer uid;
    private String solutionContent;
    private Integer likeCount;
    private Timestamp createdAt;

    public Solution(Integer pid, Integer uid, String solutionContent) {
        this.pid = pid;
        this.uid = uid;
        this.solutionContent = solutionContent;
        this.likeCount = 0;
    }
}
