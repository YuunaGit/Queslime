package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Reply;
import com.queslime.entity.Solution;
import com.queslime.entity.User;
import com.queslime.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class ReplyService {
    @Resource
    private ReplyMapper replyMapper;

    @Resource
    private UserService userService;

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

    public HashMap<String, Object> replyWrapper(Reply reply) {
        var data = new HashMap<String, Object>();

        data.put("rid", reply.getRid());
        data.put("sid", reply.getSid());
        User user  = userService.selectOneByUid(reply.getPostUid());
        data.put("post_uid", user.getUid());
        data.put("user_name", user.getUserName());
        data.put("content", reply.getReplyContent());
        data.put("created_at", reply.getCreatedAt());

        return data;
    }
}
