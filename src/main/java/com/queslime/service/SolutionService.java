package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Problem;
import com.queslime.entity.Reply;
import com.queslime.entity.Solution;
import com.queslime.entity.User;
import com.queslime.mapper.SolutionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class SolutionService {
    @Resource
    private SolutionMapper solutionMapper;

    @Resource
    private UserLikeSolutionService userLikeSolutionService;

    @Resource
    private ReplyService replyService;

    @Resource
    private UserService userService;

    @Resource
    private ProblemService problemService;

    // Create
    public int insert(Solution solution) {
        return solutionMapper.insert(solution);
    }

    // Update
    public int update(Solution solution) {
        return solutionMapper.updateById(solution);
    }

    // Retrieve
    public Solution selectOneBySid(long sid) {
        return solutionMapper.selectById(sid);
    }

    // Retrieve
    public long selectCount() {
        return solutionMapper.selectCount(null);
    }

    // Retrieve
    public List<Solution> selectListByPid(int pid) {
        return solutionMapper.selectList(new QueryWrapper<Solution>().eq("pid", pid));
    }

    // Retrieve
    public List<Solution> selectListByUid(long uid) {
        return solutionMapper.selectList(new QueryWrapper<Solution>().eq("uid", uid));
    }

    public long selectSolutionCountByPid(long pid) {
        return solutionMapper.selectCount(new QueryWrapper<Solution>().eq("pid", pid));
    }

    public List<Solution> selectMaxLikeCountSolutionList() {
        return solutionMapper.selectList(new QueryWrapper<Solution>().orderByDesc("like_count"));
    }

    // Wrapper
    public HashMap<String, Object> solutionSimpleWrapper(User thisUser, Solution solution) {
        var data = new HashMap<String, Object>();
        data.put("sid", solution.getSid());
        data.put("uid", solution.getUid());
        data.put("pid", solution.getPid());
        Problem problem = problemService.selectOneByPid(solution.getPid());
        data.put("problem_content", problem.getProblemContent());
        User user = userService.selectOneByUid(solution.getUid());
        data.put("user_name", user.getUserName());
        data.put("solution_content", solution.getSolutionContent());
        data.put("created_at", solution.getCreatedAt());
        data.put("like_count", solution.getLikeCount());
        if(userLikeSolutionService.selectOne(thisUser.getUid(), solution.getSid()) != null) {
            data.put("is_liked", true);
        } else {
            data.put("is_liked", false);
        }
        return data;
    }

    // Wrapper
    public HashMap<String, Object> solutionWrapper(User thisUser, Solution solution) {
        var data = new HashMap<String, Object>();
        data.put("sid", solution.getSid());
        data.put("uid", solution.getUid());
        User user = userService.selectOneByUid(solution.getUid());
        data.put("user_name", user.getUserName());
        data.put("content", solution.getSolutionContent());
        data.put("created_at", solution.getCreatedAt());
        data.put("like_count", solution.getLikeCount());
        if(userLikeSolutionService.selectOne(thisUser.getUid(), solution.getSid()) != null) {
            data.put("is_liked", true);
        } else {
            data.put("is_liked", false);
        }

        var replyWrapperList = new ArrayList<HashMap<String, Object>>();

        var replyList = replyService.selectListBySid(solution.getSid());
        for(Reply r : replyList) {
            replyWrapperList.add(replyService.replyWrapper(r));
        }
        data.put("reply", replyWrapperList);

        return data;
    }

    // Service
    public int stringToSid(String sidString) {
        int sid;
        try {
            sid = Integer.parseInt(sidString);
        } catch (NumberFormatException e) {
            return 0;
        }
        if(sid < 1) {
            return 0;
        }
        return sid;
    }
}
