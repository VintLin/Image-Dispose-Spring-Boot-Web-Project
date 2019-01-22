package com.felink.service.common.error;

/**
 * 视频格式不支持异常
 * @author linwentao
 * @see com.felink.service.ffmpeg.core.Executor
 */
public class VideoTypeException extends Exception {
    private String type;

    public VideoTypeException(String type)
    {
        this.type = type;
    }

    public String getType()
    {
        return type;
    }

    public String getError()
    {
        return "Video Type Error " + type;
    }
}
