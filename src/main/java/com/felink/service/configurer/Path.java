package com.felink.service.configurer;

import java.io.File;

public class Path {
    private static String ABSOLUTE_PATH = new File("").getAbsolutePath();
    public static String EXECUTE_PATH = ABSOLUTE_PATH + "\\src\\main\\executable\\";
    public static String FFMPEG_PATH = EXECUTE_PATH + "ffmpeg.exe";
    public static String MENCODER_PATH = EXECUTE_PATH + "mencoder.exe";

    public static String DISPOSE_PATH = ABSOLUTE_PATH + "\\src\\main\\resources\\disposetemp\\";
    private static String DYNAMIC_PATH = DISPOSE_PATH + "dynamic\\";
    public static String DYNAMIC_INPUT_PATH = DYNAMIC_PATH + "input\\";
    public static String DYNAMIC_OUTPUT_PATH = DYNAMIC_PATH + "output\\";
    public static String DYNAMIC_TEMP_PATH = DYNAMIC_PATH + "temp\\";

    private static String EFFECTS_PATH = DISPOSE_PATH + "effects\\";
    public static String EFFECTS_INPUT_PATH = EFFECTS_PATH + "input\\";
    public static String EFFECTS_OUTPUT_PATH = EFFECTS_PATH + "output\\";
    public static String EFFECTS_TEMP_PATH = EFFECTS_PATH + "temp\\";
    public static String EFFECTS_VIDEO_PATH = EFFECTS_PATH + "video\\";
    public static String EFFECTS_WATERMARK_PATH = EFFECTS_PATH + "watermark\\";
    public static String EFFECTS_EXAMPLE_PATH = EFFECTS_PATH + "example\\";

    private static String TRANSITIONS_PATH = DISPOSE_PATH + "transitions\\";
    public static String TRANSITIONS_INPUT_PATH = TRANSITIONS_PATH + "input\\";
    public static String TRANSITIONS_OUTPUT_PATH = TRANSITIONS_PATH + "output\\";
    public static String TRANSITIONS_TEMP_PATH = TRANSITIONS_PATH + "temp\\";
    public static String TRANSITIONS_VIDEO_PATH = TRANSITIONS_PATH + "video\\";
    public static String TRANSITIONS_IMAGE_PATH = TRANSITIONS_PATH + "image\\";
    public static String TRANSITIONS_EXAMPLE_PATH = TRANSITIONS_PATH + "example\\";

    public static String WATERMARK_PATH = DISPOSE_PATH + "watermark\\";
    public static String BACKGROUND_PATH = Path.DISPOSE_PATH + "background\\";
    public static String AUDIO_PATH = Path.DISPOSE_PATH + "audio\\";
    public static String LOG_PATH = Path.DISPOSE_PATH + "log\\";
    public static String STATIC_PATH = Path.ABSOLUTE_PATH + "\\src\\main\\resources\\static\\";
}
