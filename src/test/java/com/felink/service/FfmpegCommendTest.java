package com.felink.service;

import com.felink.project.Application;
import com.felink.service.common.error.VideoFilePathException;
import com.felink.service.common.error.VideoTypeException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@Transactional
@Rollback
public class FfmpegCommendTest {
    @Test
    public void FfmpegCommendTest() throws IOException, VideoFilePathException, VideoTypeException {
        long startTime = System.currentTimeMillis();
//        Executor.getInstance().setwords("3.MP4", "s3.MP4", "添加文字样式\nHello World！", Path.FONT_FZSTK);
//        Executor.getInstance().imageToVideo("%d.jpg", "image2video.avi");
//        Executor.getInstance().addTransitions("3.MP4", "字体1.MP4", "transitions.avi");
//        Executor.getInstance().imageToGif("%d-times.jpg", "image2gif.gif");
//        Executor.getInstance().setWaterMark("3.MP4", "1.jpeg", "s.MP4");
//        Liquify l = new Liquify(new java.awt.Point(1000, 700), new java.awt.Point(700, 500), 400, 100);
//        l.liquifyImage(Path.INPUT_PATH + "0-times.jpg", Path.INPUT_PATH + "new_image.jpg");
        long endTime = System.currentTimeMillis();
        System.out.println("程序"+ (endTime-startTime) +"s");
    }

}
