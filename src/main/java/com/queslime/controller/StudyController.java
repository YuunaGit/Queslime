package com.queslime.controller;

import com.queslime.entity.Problem;
import com.queslime.entity.Study;
import com.queslime.entity.Tag;
import com.queslime.entity.User;
import com.queslime.enums.Info;
import com.queslime.service.ProblemService;
import com.queslime.service.ProblemWithTagsService;
import com.queslime.service.StudyService;
import com.queslime.service.UserService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class StudyController {
    @Resource
    private StudyService studyService;

    @Resource
    private UserService userService;

    @Resource
    private ProblemService problemService;

    @Resource
    private ProblemWithTagsService problemWithTagsService;

    @RequestMapping(value = "/next")
    public Result getNextProblem(@RequestParam(value = "uid", defaultValue = "")String uidString,
                                 @RequestParam(value = "pid", defaultValue = "")String pidString,
            @RequestParam(value = "pass", defaultValue = "") String passString) {
        Result result = new Result();

        if ("".equals(pidString)) {
            return result.info(Info.PID_NULL);
        }

        if ("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        if ("".equals(passString)) {
            return result.info(Info.CONTENT_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if (uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if (user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }

        int pid = problemService.stringToPid(pidString);
        if (pid == 0) {
            return result.info(Info.PID_ILLEGAL);
        }

        Problem problem = problemService.selectOneByPid(pid);
        if (problem == null) {
            return result.info(Info.PID_NOT_EXISTS);
        }

        int pass;
        try {
            pass = Integer.parseInt(passString);
        } catch (NumberFormatException e) {
            return result.info(Info.STUDY_PARAM_ILLEGAL);
        }

        if (pass != 1 && pass != 0) {
            return result.info(Info.STUDY_PARAM_ILLEGAL);
        }

        var newStudy = new Study(uid, pid, pass);

        if (studyService.insert(newStudy) == 0) {
            return result.info(Info.FAIL);
        }
        
        var passList = (ArrayList<Study>) studyService.selectPassListByUid(uid);
        var notPassList = (ArrayList<Study>) studyService.selectNotPassListByUid(uid);

        int[][] modelMap = getModelMap(passList, notPassList);

        System.out.println(Arrays.deepToString(modelMap));

        var recommendTids = new ArrayList<Integer>();

        int maxTid = 0;
        for (int i = 0; i < modelMap[0].length; i++) {
            int thisMinus = modelMap[1][i] - modelMap[0][i];
            int maxMinus = modelMap[1][maxTid] - modelMap[0][maxTid];
            if (thisMinus > maxMinus) {
                maxTid = i;
            }
        }

        var tempTid = new ArrayList<Integer>(1);
        tempTid.add(maxTid);

        var pidList = problemWithTagsService.selectProblemsIdByTags(tempTid);

        Integer returnPid = pidList.get(pidList.size() - 1);

        pidList.removeAll(passPidList);

        if (!pidList.isEmpty()) {
            returnPid = pidList.get(pidList.size() - 1);
        }

        Problem nextProblem = problemService.selectOneByPid(pid);

        var data = problemService.problemWrapper(user, nextProblem);

        return result.info(Info.SUCCESS, data);
    }
    
    @RequestMapping(value = "get/plan")
    public Result getRecommend(@RequestParam(value = "uid", defaultValue = "") String uidString) {
        Result result = new Result();

        if ("".equals(uidString)) {
            return result.info(Info.UID_NULL);
        }

        int uid = userService.stringToUid(uidString);
        if (uid == 0) {
            return result.info(Info.UID_ILLEGAL);
        }

        User user = userService.selectOneByUid(uid);
        if (user == null) {
            return result.info(Info.UID_NOT_EXISTS);
        }
        
        var passList = (ArrayList<Study>) studyService.selectPassListByUid(uid);
        var notPassList = (ArrayList<Study>) studyService.selectNotPassListByUid(uid);

        int passProblemCount = passList.size();
        int notPassProblemCount = notPassList.size();
        int studyProblemCount = passProblemCount + notPassProblemCount;
        double totalPassRate = passProblemCount / studyProblemCount;

        int[][] modelMap = getModelMap(passList, notPassList);

        int passCountMaxTagId = 0;
        int notPassCountMaxTagId = 0;

        for (int i = 0; i < modelMap[0].length; i++) {
            int thisPassCount = modelMap[0][i];
            int thisNotPassCount = modelMap[1][i];
            if (thisPassCount > modelMap[0][passCountMaxTagId]) {
                passCountMaxTagId = i;
            }
            if (thisNotPassCount > modelMap[0][notPassCountMaxTagId]) {
                notPassCountMaxTagId = i;
            }
        }

        double[] passRate = new double[100];

        for (int i = 0; i < modelMap[0].length; i++) {
            passRate[i] = modelMap[0][i] / (modelMap[0][i] + modelMap[1][i]);
        }

        double maxPassRate = 0;
        double minPassRate = 100;
        for (double d : passRate) {
            if (d > maxPassRate) {
                maxPassRate = d;
            } else if (d < minPassRate & d != 0) {
                minPassRate = d;
            }
        }

        var passRateTreeSet = TreeSet<>
        for (int i = 0; i < 2; i++) {
            int thisMinPassRate
            for (int j = 0; j < modelMap.length; j++) {
                
            }
        }

        // 做了多少题 studyProblemCount
        // 综合正确率 totalPassRate
        // 你对某TAG的题目做对的次数最多，会减少此类推荐 passCountMaxTagId
        // 你对某TAG的题目做错的次数最少，会增肌推荐 notPassCountMaxTagId
        // 正确率最多的TAG maxPassRate
        // 正确率最低的TAG minPassRate

        return result;
    }

    private int[][] getModelMap(ArrayList<Study> passList, ArrayList<Study> notPassList) {
        ArrayList<Integer> passPidList = new ArrayList<>();
        for (Study s : passList) {
            passPidList.add(s.getPid());
        }

        var passTagsIdList = new ArrayList<Integer>();
        for (Study s : passList) {
            passTagsIdList.addAll(problemWithTagsService.selectTagsByPid(s.getPid()));
        }

        var notPassTagsIdList = new ArrayList<Integer>();
        for (Study s : notPassList) {
            notPassTagsIdList.addAll(problemWithTagsService.selectTagsByPid(s.getPid()));
        }

        var modelMap = new int[2][100];

        for (Integer tid : passTagsIdList) {
            modelMap[0][tid] += 1;
        }

        for (Integer tid : notPassTagsIdList) {
            modelMap[1][tid] += 1;
        }
        return modelMap;
    }

}
