package com.felink.service.ffmpeg.model;

import com.felink.service.common.utility.FileUtil;
import com.felink.service.ffmpeg.core.MencoderCommand;
import com.felink.service.common.error.VideoFilePathException;
import com.felink.service.common.error.VideoTypeException;
import com.felink.service.ffmpeg.type.NonsupportTypeEnum;
import com.felink.service.ffmpeg.type.SupportVideoTypeEnum;

public class FfmpegInputVideo extends FfmpegBaseVideo{


    public FfmpegInputVideo(String fileName) throws VideoFilePathException, VideoTypeException {
        super(fileName);
        if(FileUtil.exist(this.getFilePath())){
            checkType();
        } else {
            throw new VideoFilePathException(fileName);
        }
    }

    private void checkType() throws VideoTypeException {
        if(NonsupportTypeEnum.exist(this.getFileSuffix())) {
            transToSupport();
        } else if(!SupportVideoTypeEnum.exist(this.getFileSuffix())) {
            throw new VideoTypeException(this.getFileSuffix());
        }
    }

    private void transToSupport(){
        String newInputPath = this.getFilePath().substring(0, this.getFilePath().lastIndexOf(".") + 1) + DEFAULT_SUFFIX;
        MencoderCommand.getInstance().transcode(this.getFilePath(), newInputPath, DEFAULT_SUFFIX);
        this.setFileSuffix(DEFAULT_SUFFIX);
        this.setFilePath(newInputPath);
    }

}
