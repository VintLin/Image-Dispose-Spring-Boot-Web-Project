package com.felink.service.ffmpeg.model;

import com.felink.service.common.utility.FileUtil;
import com.felink.service.ffmpeg.core.FfmpegCommand;
import com.felink.service.common.error.VideoTypeException;
import com.felink.service.ffmpeg.type.SupportVideoTypeEnum;

public class FfmpegBaseVideo {
    final static String DEFAULT_SUFFIX = "avi";
    private String fileName;
    private String fileSuffix;
    private String filePath;

    FfmpegBaseVideo(String filePath) {
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

    public void transcode(String type) throws Exception {
        if (SupportVideoTypeEnum.exist(type)) {
            String newInputPath = this.getFilePath().substring(0, this.getFilePath().lastIndexOf(".") + 1) + DEFAULT_SUFFIX;
            FfmpegCommand.getInstance().transcode(this.getFilePath(), newInputPath);
            this.setFileSuffix(DEFAULT_SUFFIX);
            this.setFilePath(newInputPath);
        } else {
            throw new VideoTypeException(type);
        }
    }
}
