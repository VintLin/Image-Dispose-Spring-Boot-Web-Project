package com.felink.service.ffmpeg.model;

import com.felink.service.common.utility.FileUtil;

public class FfmpegImage {
    private String fileName;
    private String fileSuffix;
    private String filePath;

    public FfmpegImage(String filePath) {
        this.filePath = filePath;
        this.fileName = FileUtil.getSubFileName(filePath);
        this.fileSuffix = FileUtil.getFileSuffix(filePath);
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    String getFileSuffix() {
        return fileSuffix;
    }

    void setFileSuffix(String fileSuffix) {
        this.fileSuffix = fileSuffix;
    }

    public String getFilePath() {
        return filePath;
    }

    void setFilePath(String filePath) {
        this.filePath = filePath;
    }
}
