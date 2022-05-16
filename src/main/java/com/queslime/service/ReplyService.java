package com.queslime.service;

import com.queslime.entity.Reply;
import com.queslime.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public Reply selectOneBySid(long rid) {
        return replyMapper.selectById(rid);
    }


}
