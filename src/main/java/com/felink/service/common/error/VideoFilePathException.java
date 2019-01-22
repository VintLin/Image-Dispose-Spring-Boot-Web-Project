package com.felink.service.common.error;

/**
 * 视频文件不存在异常
 * @author linwentao
 * @see com.felink.service.ffmpeg.core.Executor
 */
public class VideoFilePathException extends Exception {
    private String path;

    public VideoFilePathException(String path)
    {
        this.path = path;
    }

    public String getPath()
    {
        return path;
    }

    public String getError()
    {
        return "File Path Error " + path;
    }
}
