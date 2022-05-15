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
@TableName(schema = "queslime", value = "r_user_like_solution")
public class UserLikeSolution {
    private Integer uid;
    private Integer sid;

    public UserLikeSolution(Integer uid, Integer sid) {
        this.uid = uid;
        this.sid = sid;
    }
}
