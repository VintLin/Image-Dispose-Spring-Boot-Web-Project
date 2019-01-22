package com.felink.project.web;
import com.felink.project.core.Result;
import com.felink.project.core.ResultGenerator;
import com.felink.project.model.ResponseJSON;
import com.felink.project.model.TemplateType;
import com.felink.project.service.TemplateTypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* Created by CodeGenerator on 2019/01/03.
*/
@RestController
@RequestMapping("/template/type")
public class TemplateTypeController {
    @Resource
    private TemplateTypeService templateTypeService;

    @PostMapping("/add")
    public Result add(TemplateType templateType) {
        templateTypeService.save(templateType);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        templateTypeService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(TemplateType templateType) {
        templateTypeService.update(templateType);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        TemplateType templateType = templateTypeService.findById(id);
        return ResultGenerator.genSuccessResult(templateType);
    }

    @GetMapping("/list")
    public Object list(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<TemplateType> list = templateTypeService.findAll();
        PageInfo pageInfo = new PageInfo(list, list.size());
        return new ResponseJSON(0, "", JSONObject.fromObject(pageInfo)).getJSON();
    }
}
