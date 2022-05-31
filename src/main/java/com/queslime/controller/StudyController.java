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

import java.util.*;

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

    @RequestMapping(value = "/study")
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

        var newStudy = new Study(user.getUid(), problem.getPid(), pass, problem.getDifficulty());

        if (studyService.insert(newStudy) == 0) {
            return result.info(Info.FAIL);
        }
        
        return result.info(Info.SUCCESS);
    }

    @RequestMapping(value = "/next/problem")
    public Result getNextProblem(@RequestParam(value = "uid", defaultValue = "")String uidString) {
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

        int[][] modelMap = getModelMap(passList, notPassList);

//        System.out.println(Arrays.deepToString(modelMap));

        double[] passRate = new double[26];

        for (int i = 1; i < modelMap[0].length; i++) {
            passRate[i] = 1.0 * modelMap[0][i] / (modelMap[0][i] + modelMap[1][i]);
        }

        int maxPassRateTid= 0;
        int minPassRateTid = 100;
        for (int i = 1; i < passRate.length; i++) {
            if (passRate[i] > maxPassRateTid) {
                maxPassRateTid = i;
            } else if (passRate[i] < minPassRateTid & passRate[i] != 0) {
                minPassRateTid = i;
            }
        }

//        System.out.println(Arrays.toString(passRate));
//        System.out.println(maxPassRateTid);
//        System.out.println(minPassRateTid);

        var returnTids = new ArrayList<Integer>();
        for(int i = 1; i <= 25; i++) {
            returnTids.add(i);
        }

        var returnPids = problemWithTagsService.selectProblemsIdByTags(returnTids);

//        System.out.println(returnPids);

        var r = new Random();

        var returnPid = returnPids.get(r.nextInt(returnPids.size()));

        Problem nextProblem = problemService.selectOneByPid(returnPid);

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

        var diff0passNum = studyService.selectCountDiffByUid(uid, 1, 0);
        var diff0NotPassNum = studyService.selectCountDiffByUid(uid, 0, 0);
        var diff0PassNotData = new HashMap<String, Long>();
        diff0PassNotData.put("pass_num", diff0passNum);
        diff0PassNotData.put("not_pass_num", diff0NotPassNum);

        var diff1passNum = studyService.selectCountDiffByUid(uid, 1, 1);
        var diff1NotPassNum = studyService.selectCountDiffByUid(uid, 0, 1);
        var diff1PassNotData = new HashMap<String, Long>();
        diff1PassNotData.put("pass_num", diff1passNum);
        diff1PassNotData.put("not_pass_num", diff1NotPassNum);

        var diff2passNum = studyService.selectCountDiffByUid(uid, 1, 2);
        var diff2NotPassNum = studyService.selectCountDiffByUid(uid, 0, 2);
        var diff2PassNotData = new HashMap<String, Long>();
        diff2PassNotData.put("pass_num", diff2passNum);
        diff2PassNotData.put("not_pass_num", diff2NotPassNum);

        int passProblemCount = passList.size();
        int notPassProblemCount = notPassList.size();
        int studyProblemCount = passProblemCount + notPassProblemCount;
        double totalPassRate = 1.0 * passProblemCount / studyProblemCount;
        String totalPassRateStr = String.format("%.2f", totalPassRate);

        int[][] modelMap = getModelMap(passList, notPassList);

        int passCountMaxTagId = 1;
        int notPassCountMaxTagId = 1;

        for (int i = 1; i < modelMap[0].length; i++) {
            int thisPassCount = modelMap[0][i];
            int thisNotPassCount = modelMap[1][i];
            if (thisPassCount > modelMap[0][passCountMaxTagId]) {
                passCountMaxTagId = i;
            }
            if (thisNotPassCount > modelMap[1][notPassCountMaxTagId]) {
                notPassCountMaxTagId = i;
            }
        }

        double[] passRate = new double[26];

        for (int i = 1; i < modelMap[0].length; i++) {
            passRate[i] = 1.0 * modelMap[0][i] / (modelMap[0][i] + modelMap[1][i]);
        }

        var everyTagPassRate = new ArrayList<HashMap<Integer, String>>();
        for(int i = 0; i < passRate.length; i++) {
            var temp = new HashMap<Integer, String>();
            temp.put(i + 1, String.format("%.2f", passRate[i]));
            everyTagPassRate.add(temp);
        }

        var data = new HashMap<String, Object>();
        data.put("total_study_problem_count", studyProblemCount);
        data.put("total_study_problem_pass_rate", totalPassRateStr);

//        data.put("pass_count_max_tag_id", passCountMaxTagId);
//        data.put("pass_count_min_tag_id", notPassCountMaxTagId);

        data.put("every_tag_pass_rate", everyTagPassRate);

        data.put("diff_0_pass_and_not_data", diff0PassNotData);
        data.put("diff_1_pass_and_not_data", diff1PassNotData);
        data.put("diff_2_pass_and_not_data", diff2PassNotData);

        var lastPassPid = passList.get(passList.size() - 1).getPid();
        Problem lastPassProblem = problemService.selectOneByPid(lastPassPid);
        data.put("last_pass_problem", problemService.problemSimpleWrapper(lastPassProblem));

        var lastNotPassPid = notPassList.get(notPassList.size() - 1).getPid();
        Problem lastNotPassProblem = problemService.selectOneByPid(lastNotPassPid);
        data.put("last_not_pass_problem", problemService.problemSimpleWrapper(lastNotPassProblem));

//        data.put("desc", "total_study_problem_count：该用户总共做了多少题；total_study_problem_pass_rate：该用户总和做题通过率；pass_count_max_tag_id：该用户做对数量最多的是哪一种题型（tag id）；" +
//                "pass_count_min_tag_id：该用户做错次数最少的是哪种题型；该用户做${tag_id_of_max_pass_rate}题型的题目通过率最高，是${max_pass_rate}；该用户做${tag_id_of_min_pass_rate}题型的题目通过率最低，是${min_pass_rate}；" +
//                "diff0passRate/diff1passRate/diff2passRate：三种难度的通过率");

        return result.info(Info.SUCCESS, data);
    }

    private int[][] getModelMap(ArrayList<Study> passList, ArrayList<Study> notPassList) {
        var passTagsIdList = new ArrayList<Integer>();
        for (Study s : passList) {
            passTagsIdList.addAll(problemWithTagsService.selectTagsByPid(s.getPid()));
        }

        var notPassTagsIdList = new ArrayList<Integer>();
        for (Study s : notPassList) {
            notPassTagsIdList.addAll(problemWithTagsService.selectTagsByPid(s.getPid()));
        }

        var modelMap = new int[2][26];

        for (Integer tid : passTagsIdList) {
            modelMap[0][tid] += 1;
        }

        for (Integer tid : notPassTagsIdList) {
            modelMap[1][tid] += 1;
        }
        return modelMap;
    }

}
