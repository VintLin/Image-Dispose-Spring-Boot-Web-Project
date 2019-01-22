package com.felink.service.ffmpeg.core;

import com.felink.service.configurer.Path;
import com.felink.service.ffmpeg.staticfield.FfmpegPosition;
import com.felink.service.ffmpeg.staticfield.FfmpegTransitions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FfmpegCommand {
    // -vf frei0r=vertigo:0.02:0.2
    // -vf frei0r=3dflippo:0.85:0.85:0.85:0.85:0.85:0.85:0.85:0.85:y:y:n
    /**
     * 水印从左往右 ffmpeg -i inputvideo.avi -vf "movie=logo.png[logo];[in][logo]overlay=x='if(gte(t\,2)\,((t-2)*80)-w\,NAN)'" outputvideo.flv
     * 隔10秒交替出现水印 ffmpeg -y -t 60 -i input.mp4 -i logo1.png -i logo2.png -filter_complex "overlay=x=if(lt(mod(t\,20)\,10)\,10\,NAN ):y=10,overlay=x=if(gt(mod(t\,20)\,10)\,W-w-10\,NAN ) :y=10" output.mp4
     *
     *
     * ffmpeg command
     * ffmpeg {1} {2} -i {3} {4} {5}
     * 1 global options 全局选项
     * 2 input file options 输入文件设置
     * 3 input url 输入文件位置
     * 4 output file options 输出文件设置
     *   -s 分辨率
     *   -r 帧速率(fps)
     *   -aspect 视频长宽比
     *   -an 取消音频
     *   -ab 设置比特率(单位：bit/s，老版 kb/s)
     *   -ac 设置声道数，1 单声道，2 立体声，转换单声道的TVrip可以用1（节省一半容量），高品质的DVDrip就可以用2
     *   -ar rate  设置音频采样率 (单位：Hz)
     *
     * 5 output url 输出文件位置
     * 淡入淡出: ffmpeg -i 1.mp4 -loop 1 -i 2.jpg -filter_complex "[1:v]fade=t=in:s=1:d=5,fade=t=out:s=15:d=3[v1]; [0:v][v1]overlay=x=0:y=0" 3.mp4
     * */
    private static Logger log = LoggerFactory.getLogger(FfmpegCommand.class);
    public static FfmpegCommand ffmpeg = new FfmpegCommand();
    private FfmpegCommand(){}

    public static FfmpegCommand getInstance(){
        return ffmpeg;
    }

    public void transcode(String inputPath, String outputPath) {
        transcode(inputPath, outputPath, 700, 900);
    }

    private void transcode(String inputPath, String outputPath, int hight, int width) {
        String[] cmd = {Path.FFMPEG_PATH, "-i", inputPath, "-ab", "56", "-ar", "22050",
            "-qscale", "8", "-r", "15", "-s", hight + "x" + width, outputPath};
        executor(cmd);
    }

    void imageToVideo(String inputPath, String outputPath, int frames) {
        String[] cmd = {Path.FFMPEG_PATH, "-threads", "2", "-y", "-r", "" + frames, "-i", inputPath,
                "-vf", "\"scale=trunc(iw/2)*2:trunc(ih/2)*2\"",
                "-pix_fmt", "yuvj420p", "-c:v", "libx264", "-strict", "-2", "-absf", "aac_adtstoasc", outputPath};
        executor(cmd);
    }
    // ffmpeg -loop 1 -framerate 1 -i dynamic.png -i music.mp3 -c:v libx264 -crf 0 -preset veryfast -tune stillimage -c:a copy -shortest output.mkv
    // ffmpeg -framerate 1/5 -i na%03d.jpg -c:v libx264 -profile:v high -crf 20 -pix_fmt yuv420p output.mp4
    void imageToVideo(String inputPath, String outputPath, String audioPath, int frames) {
        String[] cmd = {Path.FFMPEG_PATH, "-threads", "2", "-y", "-r", "" + frames, "-i", inputPath, "-i", audioPath,
                "-vf", "\"scale=trunc(iw/2)*2:trunc(ih/2)*2\"", "-pix_fmt", "yuvj420p", "-t", "5", "-c:v", "libx264",
                "-strict", "-2", "-absf", "aac_adtstoasc", outputPath};//  "-vcodec", "mpeg4",
//        String[] cmd1 = {Path.FFMPEG_PATH, "-y", "-i", Path.OUTPUT_PATH + "s.MP4", "-c:v", "libx264", "-strict", "-2", outputPath};
//        "e:\ffmpeg\ffmpeg.exe" -r 1/5 -start_number 0 -i "E:\images\01\padlock%3d.png" -c:v libx264 -r 30 -pix_fmt yuv420p e:\out.mp4
//        String[] cmd = {Path.FFMPEG_PATH, "-y", "-r", "20", "-start_number", "0", "-i", inputPath, "-i", audioPath, "-c:v", "libx264",
//                "-vf", "\"fps=25,format=yuv420p\"", outputPath};
//        String[] cmd = {Path.FFMPEG_PATH, "-framerate", "1", "-pattern_type", "glob", "-i", inputPath, "-c:v", "libx264",
//                "-r", "30", "-pix_fmt", "yuv420p", outputPath};
        executor(cmd);
//        executor(cmd1);
    }

    void getAudioFromVideo(String inputPath, String outputPath) {
        String[] cmd = {Path.FFMPEG_PATH, "-i", inputPath, "-vn", "-ab", "256", outputPath};
        executor(cmd);
    }

    void setVideoAudio(String inputPath, String audioPath, String outputPath) {
        String[] cmd = {Path.FFMPEG_PATH, "-i", audioPath, "-i", inputPath, outputPath};
        executor(cmd);
    }

    void setVideoInVideo(String inputPath1, String inputPath2, String audioPath, String outputPath) {
        String[] cmd = {Path.FFMPEG_PATH, "-y", "-i", inputPath1, "-i", inputPath2, "-i", audioPath, "-t", "8", "-absf",
                "aac_adtstoasc", "-filter_complex", "overlay=(W-w)/2:(H-h)/2", outputPath};
        executor(cmd);
    }

    void blackToBlankOverlayVideo(String inputPath1, String inputPath2, String outPath) {
        String[] cmd = {Path.FFMPEG_PATH, "-y", "-i", inputPath1, "-i",inputPath2, "-filter_complex",
                "\"[1]split[m][a];[a]geq='if(gt(lum(X,Y),16),255,0)',hue=s=0[al];[m][al]alphamerge[ovr];[0][ovr]overlay\"",
                outPath};
        executor(cmd);
    }

    void imageToGif(String inputPath, String outputPath) {
        String[] cmd = {Path.FFMPEG_PATH, "-f", "image2", "-y", "-framerate", "5", "-i",
                inputPath, outputPath};
        executor(cmd);
    }

    void addTransitions(String aVideo, String bVideo, String outputPath, int kind) {
        String transitions;
        switch(kind){
            case FfmpegTransitions.LEFT_TO_RIGHT:{
                transitions = "\"[1:v][0:v] overlay=x='if(gte(t, 1), -h+(t-2)*800, NAN)':y=0\"";
            }break;
            case FfmpegTransitions.TOP_TO_BOTTOM:{
                transitions = "\"[1:v][0:v] overlay=y='if(gte(t, 1), -h+(t-2)*800, NAN)':x=0\"";
            }break;
            default:{
                transitions = "\"[1:v][0:v] overlay=y='if(gte(t, 1), -h+(t-2)*800, NAN)':x=0\"";
            }
        }
        String[] cmd = {Path.FFMPEG_PATH, "-i", aVideo, "-i", bVideo, "-y",
                "-filter_complex", transitions, outputPath};
        executor(cmd);
    }

    /**
    * fonts path: "C\\:/Windows/fonts/BROADW.TTF"
    */
    void setWords(String inputPath, String outputPath, String content, String fontPath, String color, int pos) {
        log.info(fontPath);
        String position;
        String size = "24";
        switch(pos){
            case FfmpegPosition.TOP_LEFT:{
                position = "x=5:y=5";
            }break;
            case FfmpegPosition.TOP_RIGHT:{
                position = String.format("x=main_w-%s-5:y=5", size);
            }break;
            case FfmpegPosition.BOTTOM_LEFT:{
                position = String.format("x=main_w-%s-5:y=main_h-%s-5", size, size);
            }break;
            case FfmpegPosition.BOTTOM_RIGHT:{
                position = String.format("x=5:y=main_h-%s-5", size);
            }break;
            case FfmpegPosition.CENTER:{
                position = String.format("x=(main_w - %s)/2:y=(main_h - %s)/2", size, size);
            }break;
            default:{
                position = "overlay=(W-w)/2:(H-h)/2";
            }
        }
        String[] cmd = {Path.FFMPEG_PATH, "-i", inputPath, "-y", "-vf", String.format("\"drawtext=fontfile=%s:text='%s':" +
                "%s:fontsize=%s:fontcolor=%s:shadowy=2\"", fontPath, content, position, size, color), outputPath};
        executor(cmd);
    }

    void setWaterMark(String inputPath,String outputPath, String waterMarkPath, int pos) {
        String position;
        switch(pos){
            case FfmpegPosition.TOP_LEFT:{
                position = "overlay=5:5";
            }break;
            case FfmpegPosition.TOP_RIGHT:{
                position = "overlay=main_w-overlay_w-5:5";
            }break;
            case FfmpegPosition.BOTTOM_LEFT:{
                position = "overlay=main_w-overlay_w-5:main_h-overlay_h-5";
            }break;
            case FfmpegPosition.BOTTOM_RIGHT:{
                position = "overlay=5:main_h-overlay_h-5";
            }break;
            case FfmpegPosition.CENTER:{
                position = "overlay=(main_w-overlay_w)/2:(main_h-overlay_h)/2";
            }break;
            default:{
                position = "overlay=(W-w)/2:(H-h)/2";
            }
        }
        String[] cmd = {Path.FFMPEG_PATH, "-i", inputPath, "-i", waterMarkPath, "-y",
                "-filter_complex", "\"" + position + "\"", "-codec:a", "copy", outputPath};

        executor(cmd);
    }

    private void executor(String[] cmd) {
        try {
            List<String> command = new ArrayList<String>(Arrays.asList(cmd));
            log.info(command.toString());
            Process videoProcess = new ProcessBuilder(command).start();
            new MessageStream(videoProcess.getInputStream()).start();
            new MessageStream(videoProcess.getErrorStream()).start();
            videoProcess.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
