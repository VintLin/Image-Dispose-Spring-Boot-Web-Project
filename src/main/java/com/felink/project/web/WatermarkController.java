package com.felink.project.web;
import com.felink.project.core.Result;
import com.felink.project.core.ResultGenerator;
import com.felink.project.model.Watermark;
import com.felink.project.service.WatermarkService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2018/12/27.
*/
@RestController
@RequestMapping("/watermark")
public class WatermarkController {
    @Resource
    private WatermarkService watermarkService;

    @PostMapping("/add")
    public Result add(Watermark watermark) {
        watermarkService.save(watermark);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        watermarkService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(Watermark watermark) {
        watermarkService.update(watermark);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        Watermark watermark = watermarkService.findById(id);
        return ResultGenerator.genSuccessResult(watermark);
    }

    @PostMapping("/list")
    public Result list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Watermark> list = watermarkService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }
}
