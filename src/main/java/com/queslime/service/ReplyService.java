package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Reply;
import com.queslime.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ReplyService {
    @Resource
    private ReplyMapper replyMapper;

    // Create
    public int insert(Reply reply) {
        return replyMapper.insert(reply);
    }

    // Update
    public int update(Reply reply) {
        return replyMapper.updateById(reply);
    }

    // Retrieve
    public Reply selectOneByRid(long rid) {
        return replyMapper.selectById(rid);
    }

    public List<Reply> selectListBySid(long sid) {
        return replyMapper.selectList(
                new QueryWrapper<Reply>().eq("sid", sid)
        );
    }


}
