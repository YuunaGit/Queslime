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
@TableName(schema = "queslime", value = "e_reply")
public class Reply {
    @TableId(type = IdType.AUTO)
    private Integer rid;
    private Integer sid;
    private Integer postUid;
    private Integer toUid;
    private String replyContent;
    private Timestamp createdAt;
}
