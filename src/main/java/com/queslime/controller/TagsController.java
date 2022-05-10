package com.queslime.controller;

import com.queslime.enums.Info;
import com.queslime.service.TagService;
import com.queslime.utils.Result;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@CrossOrigin
@RestController
public class TagsController {
    @Resource
    private TagService tagService;

    @RequestMapping(value = "/get/all/tags")
    public Result getAllTags() {
        Result result = new Result();

        var tagsList = tagService.selectAllTags();
        if(tagsList == null) {
            return result.info(Info.FAIL);
        }

        result.setData(tagsList);
        return result.info(Info.SUCCESS);
    }

    @RequestMapping(value = "/get/tags")
    public Result getTagsByType(@RequestParam(value = "type", defaultValue = "")String typeIdString) {
        Result result = new Result();

        int typeId;
        try{
            typeId = Integer.parseInt(typeIdString);
        } catch (NumberFormatException e) {
            return result.info(Info.PROBLEM_TAG_ILLEGAL);
        }

        var tagsList = tagService.selectTagsByType(typeId);
        if(tagsList == null) {
            return result.info(Info.FAIL);
        }

        return result.info(Info.SUCCESS, tagsList);
    }
}
