package com.queslime.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.queslime.entity.Solution;
import com.queslime.mapper.SolutionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Service
public class SolutionService {
    @Resource
    private SolutionMapper solutionMapper;

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
        return solutionMapper.selectList(
            new QueryWrapper<Solution>().eq("pid", pid)
        );
    }

    // Retrieve
    public List<Solution> selectOneByUid(long uid) {
        return solutionMapper.selectList(
          new QueryWrapper<Solution>().eq("uid", uid)
        );
    }

    // Wrapper
    public HashMap<String, Object> solutionWrapper(Solution solution) {
        var data = new HashMap<String, Object>();
        data.put("sid", solution.getSid());
        data.put("uid", solution.getUid());
        data.put("content", solution.getSolutionContent());
        data.put("created_at", solution.getCreatedAt());
        data.put("like_count", solution.getLikeCount());
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
