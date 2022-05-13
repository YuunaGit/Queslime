package com.queslime.service;

import com.queslime.mapper.ReplyMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class ReplyService {
    @Resource
    private ReplyMapper replyMapper;

    
}
