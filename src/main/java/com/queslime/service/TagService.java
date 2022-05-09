package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Tag;
import com.queslime.mapper.TagMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TagService {
    @Resource
    private TagMapper tagMapper;

    // Create
    public int insert(Tag tag) {
        return tagMapper.insert(tag);
    }

    // Retrieve
    public List<Tag> selectAllTags() {
        return tagMapper.selectList(null);
    }

    public List<Tag> selectTagsByType(int typeId) {
        return tagMapper.selectList(
            new QueryWrapper<Tag>().eq("tag_type", typeId)
        );
    }

}
