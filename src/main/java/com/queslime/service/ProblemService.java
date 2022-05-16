package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Problem;
import com.queslime.entity.Solution;
import com.queslime.entity.Tag;
import com.queslime.entity.User;
import com.queslime.mapper.ProblemMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

@Service
public class ProblemService {
    @Resource
    private ProblemMapper problemMapper;

    @Resource
    private UserService userService;

    @Resource
    private SolutionService solutionService;

    @Resource
    private ProblemWithTagsService problemWithTagsService;

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

    public List<Problem> selectListByTags(ArrayList<Integer> tagsIdList) {
        var problemsIdList = problemWithTagsService.selectProblemsIdByTags(tagsIdList);

        return problemMapper.selectList(
            new QueryWrapper<Problem>().in("pid", problemsIdList)
        );
    }

    // Wrapper
    public HashMap<String, Object> problemSimpleWrapper(Problem problem) {
        var data = new HashMap<String, Object>();

        var tidList = problemWithTagsService.selectTagsByPid(problem.getPid());
        data.put("tag", tidList);

        data.put("pid", problem.getPid());
        data.put("content", problem.getProblemContent());
        data.put("view_count", problem.getViewCount());
        data.put("solution_count", problem.getSolutionCount());
        data.put("created_at", problem.getCreatedAt());
        return data;
    }

    // Wrapper
    public HashMap<String, Object> problemWrapper(User thisUser, Problem problem) {
        var data = new HashMap<String, Object>();

        User user = userService.selectOneByUid(problem.getUid());
        data.put("user", userService.userWrapper(user));

        var tidList = problemWithTagsService.selectTagsByPid(problem.getPid());
        data.put("tag", tidList);

        var solution = new ArrayList<HashMap<String, Object>>();
        var solutionList = solutionService.selectListByPid(problem.getPid());
        for(Solution s : solutionList) {
            solution.add(solutionService.solutionWrapper(thisUser, s));
        }
        data.put("solution", solution);

        data.put("pid", problem.getPid());
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
