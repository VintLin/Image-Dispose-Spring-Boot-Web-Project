package com.felink.service.ffmpeg.core;

import com.felink.service.configurer.Path;

import java.util.ArrayList;
import java.util.List;

public class MencoderCommand {
    private static MencoderCommand mencoder = new MencoderCommand();
    private MencoderCommand(){}

    public static MencoderCommand getInstance(){
        return mencoder;
    }

    public void transcode(String inputPath, String outputPath, String type) {
        try {
            List<String> command = new ArrayList<String>();
            command.add(Path.MENCODER_PATH);
            command.add(inputPath);
            command.add("-oac");
            command.add("lavc");
            command.add("-lavcopts");
            command.add("acodec=mp3:abitrate=64");
            command.add("-ovc");
            command.add("xvid");
            command.add("-xvidencopts");
            command.add("bitrate=600");
            command.add("-of");
            command.add(type);
            command.add("-o");
            command.add(outputPath);
            //调用线程命令启动转码
            Process videoProcess = new ProcessBuilder(command).redirectErrorStream(true).start();
            new MessageStream(videoProcess.getInputStream()).start();
            videoProcess.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
