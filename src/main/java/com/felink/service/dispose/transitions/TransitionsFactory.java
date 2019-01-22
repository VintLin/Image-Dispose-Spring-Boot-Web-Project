package com.felink.service.dispose.transitions;

import com.felink.service.configurer.Path;
import com.felink.service.common.error.VideoFilePathException;
import com.felink.service.common.error.VideoTypeException;
import com.felink.service.dispose.transitions.transitions.AbstractTransitions;
import com.felink.service.dispose.transitions.transitions.CircleTransitions;
import com.felink.service.dispose.transitions.transitions.MultiCircleTransitions;
import com.felink.service.dispose.transitions.transitions.RectangleTransitions;
import com.felink.service.common.utility.FileUtil;
import com.felink.service.ffmpeg.core.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.LinkedList;

public class TransitionsFactory {
    public static final int MULTICIRCLE = 0x01;
    public static final int CIRCLE = 0x02;
    public static final int RECTANGLE = 0x03;

    public static final int Trans_1 = 0x00;
    public static final int Trans_2 = 0x01;//TODO
    public static final int Trans_3 = 0x02;//TODO

    private static String AUDIO = Path.AUDIO_PATH + "audio_1.mp3";
    private static String BLACK_BACKGROUND = Path.BACKGROUND_PATH + "720x1080background_video.mp4";
    private static String tempPath;
    private static Logger log = LoggerFactory.getLogger(TransitionsFactory.class);

    public static String doTransitions(String token, int trans[], LinkedList<String> images) {
        tempPath = Path.TRANSITIONS_TEMP_PATH + token + File.separator;
        String inputPath = tempPath + "%d." + FileUtil.getFileSuffix(images.get(0));
        String outputPath = Path.TRANSITIONS_OUTPUT_PATH + token + ".mp4";
        AbstractTransitions transitions;
        int index = 0;
        for(int i : trans) {
            transitions = getTransitions(i, images.pop(), images.get(0), index);
            index = transitions.doTransform();
        }
        try {
            Executor.getInstance().setVideoInVideo(BLACK_BACKGROUND, inputPath, AUDIO, outputPath);
        } catch (VideoTypeException | VideoFilePathException e) {
            e.printStackTrace();
        }
        return token + ".mp4";
    }

    public static int[] getTrans(int tempId) {
        switch (tempId) {
            case Trans_1: {
                return new int[]{MULTICIRCLE, CIRCLE, RECTANGLE};
            }
            case Trans_2: {
                return new int[]{CIRCLE, MULTICIRCLE, RECTANGLE};
            }
            case Trans_3: {
                return new int[]{RECTANGLE, CIRCLE, MULTICIRCLE};
            }
            default: {
                return new int[]{MULTICIRCLE, CIRCLE, RECTANGLE};
            }
        }
    }

    private static AbstractTransitions getTransitions(int tran, String file1, String file2, int index) {
        switch (tran) {
            case MULTICIRCLE: {
                return new MultiCircleTransitions(file1, file2, tempPath, index);
            }
            case CIRCLE: {
                return new CircleTransitions(file1, file2, tempPath, index);
            }
            case RECTANGLE: {
                return new RectangleTransitions(file1, file2, tempPath, index);
            }
            default:{
                return new RectangleTransitions(file1, file2, tempPath, index);
            }
        }
    }
}
