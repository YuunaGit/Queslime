package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Problem;
import com.queslime.entity.User;
import com.queslime.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

@Service
public class ProblemService {
    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private UserService userService;

    // Create
    public int insert(Problem problem) {
        return problemMapper.insert(problem);
    }

    // Update
    public int update(Problem problem) {
        return problemMapper.insert(problem);
    }

    // Retrieve
    public Problem selectOneByPid(int pid) {
        return problemMapper.selectById(pid);
    }

    public int selectCount() {
        return Math.toIntExact(problemMapper.selectCount(null));
    }

    public List<Problem> selectListAll() {
        return problemMapper.selectList(null);
    }

    public List<Problem> selectListBySearch(String search) {
        return problemMapper.selectList(
            new QueryWrapper<Problem>().like("problem_content", search)
        );
    }

    public List<Problem> selectListByTags(int[] tagsId) {

    }

    // Wrapper
    public HashMap<String, Object> problemWrapper(Problem problem) {
        var data = new HashMap<String, Object>();
        data.put("pid", problem.getPid());
        User user = userService.selectOneByUid(problem.getUid());
        data.put("user_name", user.getUid());
        data.put("content", problem.getProblemContent());
        data.put("created_at", problem.getCreatedAt());
        return data;
    }

    // Service
    public int stringToPid(String pidString) {
        int pid;
        try {
            pid = Integer.parseInt(pidString);
        } catch (NumberFormatException e) {
            return 0;
        }
        if(pid < 1) {
            return 0;
        }
        return pid;
    }
}
