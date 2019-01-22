package com.felink.service.dispose.effects;

import com.felink.service.configurer.Path;
import com.felink.service.common.error.VideoFilePathException;
import com.felink.service.common.error.VideoTypeException;
import com.felink.service.dispose.effects.effects.SurnameEffects;
import com.felink.service.dispose.effects.effects.TwoSentencesEffects;
import com.felink.service.dispose.effects.effects.WordEffects;
import com.felink.service.dispose.effects.watermark.SurnameWatermarkUtils;
import com.felink.service.dispose.effects.watermark.TwoSentencesWaterMarkUtils;
import com.felink.service.dispose.effects.watermark.WordWatermarkUtils;
import com.felink.service.ffmpeg.core.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class EffectsFactory {
    private static Logger logger = LoggerFactory.getLogger(EffectsFactory.class);
    private static final int SURNAME = 0x00;
    private static final int TWOSENTENCES = 0x01;
    private static final int WORD = 0x02;

    private static String EFFECT_AUDIO_1 = Path.AUDIO_PATH + "audio_1.mp3";
    private static String EFFECT_VIDEO_1 = Path.EFFECTS_VIDEO_PATH + "effects_video_1.mp4";
    private static String EFFECT_VIDEO_2 = Path.EFFECTS_VIDEO_PATH + "effects_video_2.mp4";
    private static String BLACK_BACKGROUND = Path.BACKGROUND_PATH + "720x1080background_video.mp4";


    private static String makeWaterMark(int tempId, String savePath, String ...contents) {
        switch (tempId) {
            case SURNAME: {
                return new SurnameWatermarkUtils(savePath, contents).disposeWatermark();
            }
            case TWOSENTENCES: {
                return new TwoSentencesWaterMarkUtils(savePath, contents).disposeWatermark();
            }
            case WORD: {
                return new WordWatermarkUtils(savePath, contents).disposeWatermark();
            }
            default: {
                return new SurnameWatermarkUtils(savePath, contents).disposeWatermark();
            }
        }
    }

    public static String doEffects(int tempId, String token, String ...content) {
        String watermarkSavePath = Path.EFFECTS_WATERMARK_PATH + token + ".png";
        String outputPath = Path.EFFECTS_OUTPUT_PATH + token + ".mp4";
        String savePath = Path.EFFECTS_TEMP_PATH  + token + File.separator;
        String waterMarkPath = makeWaterMark(tempId, watermarkSavePath, content);
        String inputPath, background_video, audio;
        switch (tempId) {
            case SURNAME: {
                background_video = BLACK_BACKGROUND;
                audio = EFFECT_AUDIO_1;
                inputPath = new SurnameEffects(waterMarkPath, savePath).getSavePath();
            }break;
            case TWOSENTENCES: {
                background_video = EFFECT_VIDEO_1;
                audio = EFFECT_AUDIO_1;
                inputPath = new TwoSentencesEffects(waterMarkPath, savePath).getSavePath();
            }break;
            case WORD: {
                background_video = EFFECT_VIDEO_2;
                audio = EFFECT_AUDIO_1;
                inputPath = new WordEffects(waterMarkPath, savePath).getSavePath();
            }break;
            default: {
                background_video = BLACK_BACKGROUND;
                audio = EFFECT_AUDIO_1;
                inputPath = new SurnameEffects(waterMarkPath, savePath).getSavePath();
            }
        }
        try {
            logger.info(inputPath + outputPath + audio);
            Executor.getInstance().setVideoInVideo(background_video, inputPath, audio, outputPath);
        } catch (VideoTypeException | VideoFilePathException e) {
            e.printStackTrace();
        }
        return token + ".mp4";
    }
}
