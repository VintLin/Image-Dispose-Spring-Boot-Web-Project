package com.felink.service.dispose.dynamic;

import com.felink.service.common.model.BasePoint;
import com.felink.service.common.model.BaseVector;
import com.felink.service.configurer.Path;
import com.felink.service.dispose.dynamic.dynamic.DynamicControler;
import com.felink.service.ffmpeg.core.Executor;

import java.io.File;
import java.util.List;

public class DynamicFactory {
    public static String doDynamic(String token, String imageSuffix, List<BaseVector> vectors, List<BasePoint> points) {
        String tempPath = Path.DYNAMIC_INPUT_PATH + token + File.separator;
        String inputPath = tempPath + "origin." + imageSuffix;
        String outputPath = Path.DYNAMIC_OUTPUT_PATH + token + ".mp4";
        DynamicControler dynamicControler = new DynamicControler(inputPath, tempPath, vectors, points);
        dynamicControler.doDynamic();
        String backgroundPath = Path.BACKGROUND_PATH + "720x1080background_video.mp4";
        String tempImagePath = tempPath + "%d." + imageSuffix;
        String audioPath = Path.AUDIO_PATH + "audio_1.mp3";
        Executor.getInstance().imageToVideo(tempImagePath, outputPath, null, 30);
        return token + ".mp4";
    }
}
