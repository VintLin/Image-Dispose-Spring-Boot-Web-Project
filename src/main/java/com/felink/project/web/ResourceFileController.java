package com.felink.project.web;

import com.felink.project.model.ResponseJSON;
import com.felink.service.common.utility.FileUtil;
import com.felink.service.configurer.Path;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/file")
public class ResourceFileController {

    @GetMapping("/path")
    public JSONObject path(@RequestParam(defaultValue = "") String path) throws IOException{
        JSONObject json = new JSONObject();
        if(path.equals("")){
            json.put("files", FileUtil.getFileList(Path.DISPOSE_PATH));
        } else {
            json.put("files", Objects.requireNonNull(FileUtil.getFileList(Path.DISPOSE_PATH + path)));
            json.put("prefix", path);
        }
        return new ResponseJSON(200, "success", json).getJSON();
    }

    @GetMapping("")
    public void file(@RequestParam(defaultValue = "") String file) throws IOException {

    }
}
