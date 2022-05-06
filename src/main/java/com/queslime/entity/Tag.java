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
@TableName(schema = "queslime", value = "e_tag")
public class Tag {
    @TableId(type = IdType.AUTO)
    private Integer tid;
    private Integer tagType;
    private String tagName;

    public Tag(Integer tagType, String tagName) {
        this.tagType = tagType;
        this.tagName = tagName;
    }
}
