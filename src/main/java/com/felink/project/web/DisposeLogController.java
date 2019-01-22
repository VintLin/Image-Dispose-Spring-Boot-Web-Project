package com.felink.project.web;

import com.felink.project.core.Result;
import com.felink.project.core.ResultGenerator;
import com.felink.project.model.DisposeLog;
import com.felink.project.model.MyPageInfo;
import com.felink.project.model.ResponseJSON;
import com.felink.project.service.DisposeLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/log")
public class DisposeLogController {
    @Resource
    private DisposeLogService disposeLogService;

    @PostMapping("/add")
    public Result add(DisposeLog log) {
        disposeLogService.save(log);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/delete")
    public Result delete(@RequestParam Integer id) {
        disposeLogService.deleteById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/update")
    public Result update(DisposeLog log) {
        disposeLogService.update(log);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/detail")
    public Result detail(@RequestParam Integer id) {
        DisposeLog log = disposeLogService.findById(id);
        return ResultGenerator.genSuccessResult(log);
    }

    @GetMapping("/list")
    public JSONObject list(@RequestParam(defaultValue = "0") Integer pageIndex,
                       @RequestParam(defaultValue = "0") Integer pageSize,
                       @RequestParam(defaultValue = "false") Boolean today,
                       @RequestParam(defaultValue = "false") Boolean week,
                       @RequestParam(defaultValue = "false") Boolean mouth,
                       @RequestParam(defaultValue = "false") Boolean year) {
        String sql;
        if(today) {
            sql = "DAY(time) = DAY(NOW())";
        } else if (week) {
            sql = "WEEK(time) = WEEK(NOW())";
        } else if (mouth) {
            sql = "MOUTH(time) = MOUTH(NOW())";
        } else{
            sql = "YEAR(time) = YEAR(NOW())";
        }
        return getListByCondition(pageIndex, pageSize, sql);
    }

    @GetMapping("/today")
    public JSONObject today(@RequestParam(defaultValue = "0") Integer pageIndex,
                        @RequestParam(defaultValue = "0") Integer pageSize) {
        return getListByCondition(pageIndex, pageSize, "DAY(time) = DAY(NOW())");
    }

    @GetMapping("/week")
    public JSONObject week(@RequestParam(defaultValue = "0") Integer pageIndex,
                            @RequestParam(defaultValue = "0") Integer pageSize) {
        return getListByCondition(pageIndex, pageSize, "WEEK(time) = WEEK(NOW())");
    }

    @GetMapping("/mouth")
    public JSONObject mouth(@RequestParam(defaultValue = "0") Integer pageIndex,
                           @RequestParam(defaultValue = "0") Integer pageSize) {
        return getListByCondition(pageIndex, pageSize, "MOUTH(time) = MOUTH(NOW())");
    }

    @GetMapping("/year")
    public JSONObject year(@RequestParam(defaultValue = "0") Integer pageIndex,
                            @RequestParam(defaultValue = "0") Integer pageSize) {
        return getListByCondition(pageIndex, pageSize, "YEAR(time) = YEAR(NOW())");
    }

    private JSONObject getListByCondition(int index, int size, String select) {
        PageHelper.startPage(index, size);
        Condition condition = new Condition(DisposeLog.class);
        condition.createCriteria().andEqualTo(select);
        condition.orderBy("id").desc();
        List<DisposeLog> list = disposeLogService.findByCondition(condition);
        PageInfo<DisposeLog> pageInfo = new PageInfo<>(list);
        MyPageInfo<DisposeLog> myPageInfo = new MyPageInfo<>(pageInfo);
        return new ResponseJSON(0, "", JSONObject.fromObject(myPageInfo)).getJSON();

    }
}
