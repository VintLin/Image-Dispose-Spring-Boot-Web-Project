package com.felink.project.web;
import com.felink.project.core.Result;
import com.felink.project.core.ResultGenerator;
import com.felink.project.model.MyPageInfo;
import com.felink.project.model.ResponseJSON;
import com.felink.project.model.VideoTemplate;
import com.felink.project.service.VideoTemplateService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.apache.avro.data.Json;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/01/03.
*/
@RestController
@RequestMapping("/api")
public class VideoTemplateController {
    @Resource
    private VideoTemplateService videoTemplateService;

    @PostMapping("/add")
    public Result add(VideoTemplate videoTemplate) {
        videoTemplateService.save(videoTemplate);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        videoTemplateService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(VideoTemplate videoTemplate) {
        videoTemplateService.update(videoTemplate);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        VideoTemplate videoTemplate = videoTemplateService.findById(id);
        return ResultGenerator.genSuccessResult(videoTemplate);
    }


    @GetMapping("/effects")
    public JSONObject effects(@RequestParam(defaultValue = "0") Integer pageIndex, @RequestParam(defaultValue = "0") Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        Condition condition = new Condition(VideoTemplate.class);
        condition.createCriteria().andEqualTo("tempType", 0);
        condition.orderBy("tempId").asc();
        List<VideoTemplate> list = videoTemplateService.findByCondition(condition);
        PageInfo<VideoTemplate> pageInfo = new PageInfo<VideoTemplate>(list);
        MyPageInfo<VideoTemplate> myPageInfo = new MyPageInfo<VideoTemplate>(pageInfo);
        return new ResponseJSON(0, "", JSONObject.fromObject(myPageInfo)).getJSON();
    }

    @GetMapping("/album")
    public JSONObject album(@RequestParam(defaultValue = "0") Integer pageIndex, @RequestParam(defaultValue = "0") Integer pageSize) {
        PageHelper.startPage(pageIndex, pageSize);
        Condition condition = new Condition(VideoTemplate.class);
        condition.createCriteria().andEqualTo("tempType", 1);
        condition.orderBy("tempId").asc();
        List<VideoTemplate> list = videoTemplateService.findByCondition(condition);
        PageInfo<VideoTemplate> pageInfo = new PageInfo<VideoTemplate>(list);
        MyPageInfo<VideoTemplate> myPageInfo = new MyPageInfo<VideoTemplate>(pageInfo);
        return new ResponseJSON(0, "", JSONObject.fromObject(myPageInfo)).getJSON();
    }

}
