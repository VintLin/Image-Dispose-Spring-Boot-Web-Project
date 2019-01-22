package com.felink.project.web;
import com.alibaba.fastjson.JSON;
import com.felink.project.model.ResponseJSON;
import com.felink.project.model.Video;
import com.felink.project.service.VideoService;
import com.felink.service.common.error.JSONContentNullException;
import com.felink.service.common.model.BaseImage;
import com.felink.service.common.model.BasePoint;
import com.felink.service.common.model.BaseVector;
import com.felink.service.common.utility.FileUtil;
import com.felink.service.common.utility.JSONUtil;
import com.felink.service.dispose.effects.EffectsFactory;
import com.felink.service.DisposeResourceService;
import net.sf.json.JSONObject;
import com.felink.service.configurer.Path;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;
import java.util.concurrent.*;


/**
* Created by CodeGenerator on 2018/11/22.
*/
@RestController
@RequestMapping("/api")
public class CompositeController {
    private static final Logger logger = LoggerFactory.getLogger(CompositeController.class);
    @Resource
    VideoService videoService;
    @ResponseBody
    @RequestMapping(value="/test", method = RequestMethod.GET)
    public Object test(HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        ExecutorService service = Executors.newFixedThreadPool(1);
        String message = "[\"Not Over\"]";
        try {
            Future<String> future = service.submit(() -> {
                Thread.sleep(4000);
                logger.info("is Done");
                return "[\"isDone\"]";
            });
            Thread.sleep(3000);
            if(future.isDone()) {
                message = future.get();
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return JSON.parse(message);
    }

    @ResponseBody
    @RequestMapping(value="/download", method = RequestMethod.GET)
    public JSONObject index(HttpServletResponse response,
                        @RequestParam(value="token")String token) {
        Condition condition = new Condition(Video.class);
        condition.createCriteria().andEqualTo("remake", token);
        List<Video> list = videoService.findByCondition(condition);
        JSONObject obj = new JSONObject();
        String message;
        int code;
        if(list.isEmpty()){
            code = 0;
            message = "There is no such file.";
            obj.put("hasFile", false);
            obj.put("download", "");
        } else {
            code = 0;
            message = "success";
            obj.put("hasFile", true);
            obj.put("download", "http://172.17.150.39:8080/temp/" + list.get(0).getVideoName());
        }
        return new ResponseJSON(code, message, obj).getJSON();
    }

    /**
     * operate 0 dynamic to ffmpeg 1 dynamic to gif
     * @param image
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/image", method = RequestMethod.POST)
    public JSONObject transitions(HttpServletResponse response,
                                  @RequestParam(value="uid", defaultValue = "null")String uid,
                                  @RequestParam(value="tempId")int tempId,
                                  @RequestParam(value="token")String token,
                                  @RequestParam(value="imageIndex")int imageIndex,
                                  @RequestParam(value="end")int end,
                                  @RequestParam(value="image")MultipartFile image) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        int code = 0;
        String message = "success";
        token = token == null || token.length() < 4? FileUtil.getUUID(): token;
        String suffix = FileUtil.getFileSuffix(image.getOriginalFilename());
        String saveFilePath = FileUtil.splicePath(Path.TRANSITIONS_INPUT_PATH, token);
        String saveFile = FileUtil.splicePath(saveFilePath,  imageIndex + "." + suffix);
        String download = token +".MP4";
        try {
            InputStream inputStream = image.getInputStream();
            FileUtil.fromStreamSave(inputStream, saveFile);
            BaseImage.tileImage(saveFile, Path.BACKGROUND_PATH + "720x1280background.png");
            if(end == 1){
                DisposeResourceService.doTransitions(tempId, token, "png");
            }
        } catch (IOException ex) {
            message = "There's no file" + ex.toString();
            code = 1;
        }

        JSONObject data = new JSONObject();
        data.put("download", "http://172.17.150.39:8080/temp/" + download);
        data.put("token", token);
        return new ResponseJSON(code, message, data).getJSON();
    }

    /**
     * 0 ffmpeg add word 1 ffmpeg add watermark 2 ffmpeg add name watermark
     * @param uid
     * @param logo
     * @param tempId
     * @param textArray
     * @return
     */
    @ResponseBody
    @RequestMapping(value="/video", method = RequestMethod.POST)
    public JSONObject effects(HttpServletResponse response,
                                     @RequestParam(value = "uid", defaultValue = "null")String uid,
                                     @RequestParam(value = "logo", defaultValue = "1")int logo,
                                     @RequestParam(value = "tempId")int tempId,
                                     @RequestParam(value = "textArray")String textArray) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String token = FileUtil.getUUID();
        String message = "success";
        int code = 0;
        String[] array = JSONUtil.getArrayByTextArray(textArray);

        String download = EffectsFactory.doEffects(tempId, token, array);

        JSONObject data = new JSONObject();
        data.put("download", "http://172.17.150.39:8080/temp/" + download);
        ResponseJSON responseJSON = new ResponseJSON(code, message, data);
        return responseJSON.getJSON();
    }

    @ResponseBody
    @RequestMapping(value="/transform",method=RequestMethod.POST)
    public JSONObject dynamic(MultipartHttpServletRequest request,
                             HttpServletResponse response,
                             @RequestParam(value = "uid", defaultValue = "null")String uid,
                             @RequestParam(value = "vector")String vector,
                             @RequestParam(value = "scope")String points,
                             @RequestParam(value = "image")MultipartFile dataFile) {

        logger.info(points);
        logger.info(vector);
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        String token = FileUtil.getUUID();
        String message = null;
        String download = "";
        boolean hasFile = false;
        int code = 0;
        try {
            uid = uid == null || uid.equals("null") ? request.getLocalAddr() : uid;
            JSONUtil.checkJSON(points);
            JSONUtil.checkJSON(vector);
            List<BaseVector> vs = JSONUtil.getVectorsByJSON(vector);
            List<BasePoint> ps = JSONUtil.getPointsByJSON(points);
            String suffix = FileUtil.getFileSuffix(dataFile.getOriginalFilename());
            String saveFile = FileUtil.splicePath(Path.DYNAMIC_INPUT_PATH + token, "origin." + suffix);
            InputStream inputStream = dataFile.getInputStream();
            FileUtil.fromStreamSave(inputStream, saveFile);

            ExecutorService service = Executors.newFixedThreadPool(1);
            Future<String> future = service.submit(() -> {
                String download1 = DisposeResourceService.doDynamic(token, suffix, vs, ps);
                Video video = new Video();
                video.setRemake(token);
                video.setVideoName(download1);
                video.setVideoPath(Path.DYNAMIC_OUTPUT_PATH + download1);
                videoService.save(video);
                return download1;
            });
            Thread.sleep(5000);
            if(future.isDone()) {
                download = "http://172.17.150.39:8080/temp/" + future.get();
                hasFile = true;
            }
        } catch (IOException ex){
            ex.printStackTrace();
            message = "There's no File." + ex.toString();
            code = 1;
        } catch (JSONContentNullException ex) {
            ex.printStackTrace();
            message = "There's no " + ex.getJSONName() + " in JSON.";
            code = 2;
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }

        JSONObject data = new JSONObject();
        data.put("hasFile", hasFile);
        data.put("download",  download);
        data.put("token", token);
        ResponseJSON responseJSON = new ResponseJSON(code, message, data);
        return responseJSON.getJSON();
    }


}