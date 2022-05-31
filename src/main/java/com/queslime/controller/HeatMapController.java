package com.queslime.controller;

import com.queslime.entity.Solution;
import com.queslime.entity.Study;
import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.SolutionService;
import com.queslime.service.StudyService;
import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

@CrossOrigin
@RestController
public class HeatMapController {
    @Resource
    private StudyService studyService;

    @Resource
    private UserService userService;

    @Resource
    private SolutionService solutionService;

    @RequestMapping(value = "heatmap")
    public Result getMap(@RequestParam(value = "uid", defaultValue = "")String uidString) {
        Result result = new Result();

        if("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if(uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if(user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        var studyDataList = (ArrayList<Study>) studyService.selectListByUid(user.getUid());
        var postSolutionDataList = (ArrayList<Solution>) solutionService.selectListByUid(user.getUid());

        HashMap<Date, Integer> heatMap = new HashMap<>();

        for(Study s : studyDataList) {
            Timestamp ts = s.getCreatedAt();
            Date d = new Date(ts.getYear(), ts.getMonth(), ts.getDate());
            if(heatMap.containsKey(d)) {
                heatMap.put(d, heatMap.get(d) + 1);
            } else {
                heatMap.put(d, 1);
            }
        }

        for(Solution s : postSolutionDataList) {
            Timestamp ts = s.getCreatedAt();
            Date d = new Date(ts.getYear(), ts.getMonth(), ts.getDate());
            if(heatMap.containsKey(d)) {
                heatMap.put(d, heatMap.get(d) + 1);
            } else {
                heatMap.put(d, 1);
            }
        }

        var returnHeatMap = new ArrayList<HashMap<String, String>>();

        for (Map.Entry entry : heatMap.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue().toString();
            var temp = new HashMap<String, String>();
            temp.put("date", key);
            temp.put("count", value);
            returnHeatMap.add(temp);
        }

        return result.info(Info.SUCCESS, returnHeatMap);
    }
}
