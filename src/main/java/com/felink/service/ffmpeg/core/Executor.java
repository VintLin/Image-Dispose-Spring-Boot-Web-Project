package com.felink.service.ffmpeg.core;

import com.felink.service.configurer.Path;
import com.felink.service.common.error.VideoFilePathException;
import com.felink.service.common.error.VideoTypeException;
import com.felink.service.ffmpeg.model.FfmpegImage;
import com.felink.service.ffmpeg.model.FfmpegInputVideo;
import com.felink.service.ffmpeg.model.FfmpegOutputVideo;
import com.felink.service.ffmpeg.staticfield.FfmpegPosition;
import com.felink.service.common.utility.FileUtil;
import com.felink.service.ffmpeg.staticfield.FfmpegTransitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Executor {
    private static int DEFAULT_POSITION = FfmpegPosition.BOTTOM_LEFT;
    private static int DEFAULT_TRANSITIONS = FfmpegTransitions.TOP_TO_BOTTOM;

    private static Logger log = LoggerFactory.getLogger(Executor.class);
    private static Executor executor = new Executor();
    private Executor() {this.checkPath();}
    public static Executor getInstance() {
        return executor;
    }

    private static String PathTurnToCommand(String cmd) {
        return cmd.replace("\\", "/").replace(":", "\\\\:");
    }

    public void addTransitions(String aVideo, String bVideo, String outputFile) throws VideoFilePathException, VideoTypeException {
        FfmpegInputVideo avideo = new FfmpegInputVideo(aVideo);
        FfmpegInputVideo bvideo = new FfmpegInputVideo(bVideo);
        FfmpegOutputVideo ovideo = new FfmpegOutputVideo(outputFile);
        FfmpegCommand.getInstance().addTransitions(avideo.getFilePath(), bvideo.getFilePath(), ovideo.getFilePath(), DEFAULT_TRANSITIONS);
    }

    public void setWaterMark(String inputFile, String waterMark, String outputFile, int position) throws VideoTypeException, VideoFilePathException {
        FfmpegInputVideo ifile = new FfmpegInputVideo(inputFile);
        FfmpegOutputVideo ofile = new FfmpegOutputVideo(outputFile);
        log.info(ofile.getFilePath() + "\n" + ifile.getFilePath() + "\n" + waterMark);
        FfmpegCommand.getInstance().setWaterMark(ifile.getFilePath(), ofile.getFilePath(), waterMark, position);
    }

    public void setwords(String inputFile, String outputFile, String content, String Font, String color, int position) throws VideoTypeException, VideoFilePathException {
        FfmpegInputVideo ifile = new FfmpegInputVideo(inputFile);
        FfmpegOutputVideo ofile = new FfmpegOutputVideo(outputFile);
        FfmpegCommand.getInstance().setWords(ifile.getFilePath(), ofile.getFilePath(), content,
                PathTurnToCommand(Font), color, position);
    }

    public void imageToVideo(String inputFile, String outputFile, String audioPath, int frames){
        FfmpegImage ifile = new FfmpegImage(inputFile);
        FfmpegOutputVideo ofile = new FfmpegOutputVideo(outputFile);
        if(audioPath == null)
            FfmpegCommand.getInstance().imageToVideo(ifile.getFilePath(), ofile.getFilePath(), frames);
        else
            FfmpegCommand.getInstance().imageToVideo(ifile.getFilePath(), ofile.getFilePath(), audioPath, frames);
    }

    public void imageToGif(String inputFile, String outputFile){
        FfmpegImage ifile = new FfmpegImage(inputFile);
        FfmpegOutputVideo ofile = new FfmpegOutputVideo(outputFile);
        FfmpegCommand.getInstance().imageToGif(ifile.getFilePath(), ofile.getFilePath());
    }

    public void getAudioFromVideo(String inputFile, String outputFile) throws VideoTypeException, VideoFilePathException {
        FfmpegInputVideo ifile = new FfmpegInputVideo(inputFile);
        FfmpegCommand.getInstance().getAudioFromVideo(ifile.getFilePath(), outputFile);
    }

    public void setVideoAudio(String inputFile, String audioPath, String outputPath) throws VideoTypeException, VideoFilePathException {
        FfmpegInputVideo ifile = new FfmpegInputVideo(inputFile);
        FfmpegOutputVideo ofile = new FfmpegOutputVideo(outputPath);
        FfmpegCommand.getInstance().setVideoAudio(ifile.getFilePath(), audioPath, ofile.getFilePath());
    }

    public void setVideoInVideo(String inputPath1, String inputPath2, String audioPath, String outputPath) throws VideoTypeException, VideoFilePathException {
        FfmpegInputVideo ifile1 = new FfmpegInputVideo(inputPath1);
        FfmpegOutputVideo ofile = new FfmpegOutputVideo(outputPath);
        FfmpegCommand.getInstance().setVideoInVideo(ifile1.getFilePath(), inputPath2, audioPath, ofile.getFilePath());
    }

    private void checkPath() {
        FileUtil.mkdirs(Path.EXECUTE_PATH);
    }

    public static void main(String[] args) throws VideoFilePathException, VideoTypeException {
//        getInstance().getAudioFromVideo(Path.EFFECTS_EXAMPLE_PATH + "s.MP4", Path.EFFECTS_EXAMPLE_PATH + "s.mp3");
        getInstance().setVideoAudio(Path.EFFECTS_EXAMPLE_PATH + "effects_1.MP4",
                Path.EFFECTS_EXAMPLE_PATH + "s.mp3",
                Path.EFFECTS_EXAMPLE_PATH + "new.MP4");
    }

}
