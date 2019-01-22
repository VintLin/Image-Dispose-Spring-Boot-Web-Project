package com.felink.service.dispose.dynamic.dynamic;

import com.felink.service.common.model.*;
import com.felink.service.common.utility.JSONUtil;
import com.felink.service.configurer.Path;
import com.felink.service.dispose.dynamic.model.DynamicFragment;
import com.felink.service.ffmpeg.core.Executor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DynamicControler {
    private static Logger logger = LoggerFactory.getLogger(DynamicControler.class);
    private BaseImage image;
    private List<BaseVector> vectors;
    private List<DynamicFragment> fragments = new LinkedList<>();

    /**
     *
     * @param inputPath 需要处理的图片路径
     * @param savePath 保存目录
     * @param vectors 形变路径
     * @param points 固定点
     */
    public DynamicControler(String inputPath, String savePath, List<BaseVector> vectors, List<BasePoint> points){
        image = new BaseImage(inputPath, savePath);
        this.vectors = vectors;
        initDynamicFragment(points);
    }

    /**
     * 初始化每一帧的参数
     * 可形变区域 实际形变区域 运动路径切片
     * @param points 所有固定点
     */
    private void initDynamicFragment(List<BasePoint> points) {
        int frame = 120;
        DelaunayPlane startMotionPlane = new DelaunayPlane(points, image.getWidth(), image.getHeight());
        for(BaseVector vector: vectors) {
            vector.initChildVector(frame);
            startMotionPlane.addPoint(vector.getFrom());
        }
        Set<BaseTriangle> fixTriangles = new HashSet<>();
        Set<BaseTriangle> motionTriangles = new HashSet<>();
        startMotionPlane.getFixAndMotionTriangles(vectors, fixTriangles, motionTriangles);
        DynamicFragment.initFragment(fixTriangles, motionTriangles, BaseImage.deepCopy(image.getOperateImage()));
        int fixFrame = (int)(frame * 0.1);
        double alpha;
        for(BaseVector vector: vectors) {
            for(int j = 0; j < frame; j++) {
                if(fragments.size() < frame) {
                    alpha = j < fixFrame ? 1 : j > frame - fixFrame? 0: 1 - (j - fixFrame) / (double) (frame - fixFrame * 2);
                    fragments.add(new DynamicFragment(image.getSaveFilePath(j), alpha));
                }
                fragments.get(j).addVector(vector.getForegroundVector(j), vector.getBackgroundVector(j));
            }
        }
    }

    /**
     * 处理所有帧, 并生成相应图片。
     */
    public void doDynamic() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(4);
        for(DynamicFragment fragment: fragments) {
            fixedThreadPool.execute(fragment);
        }
        fixedThreadPool.shutdown();
        try {
            //等待直到所有任务完成
            fixedThreadPool.awaitTermination(Long.MAX_VALUE, TimeUnit.MINUTES);
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        fragments.get(40).run();
//        fragments.get(39).run();
//        fragments.get(fragments.size() - 2).run();
    }


    public static void main(String[] args) {
        String path = Path.DYNAMIC_INPUT_PATH + "test-dynamic-do" + File.separator;
        String vector = "[{\"begin\":[30.5,377],\"end\":[87.16666412353516,367.3333435058594]},{\"begin\":[113.83333587646484,361],\"end\":[168.5,326]},{\"begin\":[30.83333396911621,421],\"end\":[85.5,414]},{\"begin\":[119.5,407.3333435058594],\"end\":[167.5,375.6666564941406]},{\"begin\":[34.16666793823242,465],\"end\":[84.5,462]},{\"begin\":[109.83333587646484,462.6666564941406],\"end\":[161.1666717529297,443]},{\"begin\":[38.83333206176758,506],\"end\":[83.16666412353516,505]},{\"begin\":[120.16666412353516,509],\"end\":[174.5,498]},{\"begin\":[40.83333206176758,553],\"end\":[87.16666412353516,552.6666870117188]},{\"begin\":[124.83333587646484,557.3333129882812],\"end\":[169.8333282470703,552.6666870117188]},{\"begin\":[190.8333282470703,553.3333129882812],\"end\":[233.5,544.6666870117188]},{\"begin\":[49.5,607],\"end\":[94.16666412353516,601.3333129882812]},{\"begin\":[130.1666717529297,610.6666870117188],\"end\":[178.1666717529297,605.3333129882812]},{\"begin\":[210.5,613.3333129882812],\"end\":[263.1666564941406,609.6666870117188]},{\"begin\":[296.5,613],\"end\":[346.8333435058594,608.3333129882812]},{\"begin\":[283.1666564941406,488.6666564941406],\"end\":[346.1666564941406,481.3333435058594]}]";
        String points = "[[264.5,164.6666717529297],[242.8333282470703,139.3333282470703],[260.8333435058594,112.33333587646484],[300.1666564941406,110.33333587646484],[312.1666564941406,144],[293.1666564941406,167.3333282470703]]";
        try {
            long startTime = System.currentTimeMillis();
            List<BaseVector> vs = JSONUtil.getVectorsByJSON(vector);
            List<BasePoint> ps = JSONUtil.getPointsByJSON(points);
            DynamicControler dynamicControler = new DynamicControler(path + "origin.png", path, vs, ps);
            dynamicControler.doDynamic();
            String tempImagePath = path + "%d.png";
            String outputPath = Path.DYNAMIC_OUTPUT_PATH + "test-dynamic-do"  + ".mp4";
            String backgroundPath = Path.BACKGROUND_PATH + "720x1080background_video.mp4";
            String audio = Path.AUDIO_PATH + "audio_1.mp3";
//            Executor.getInstance().setVideoInVideo( backgroundPath, tempImagePath, audio, outputPath);
            Executor.getInstance().imageToVideo(tempImagePath, outputPath, null, 30);
            long endTime = System.currentTimeMillis();
            System.out.println("程序"+ (endTime-startTime) +"s");
        } catch(Exception ex) {
            ex.printStackTrace();
        }

//        String path = Path.DYNAMIC_INPUT_PATH + "test-dynamic-do" + File.separator + "origin.png";
//        BaseImage image = new BaseImage(path, null);
//        BufferedImage bufferedImage = image.getOperateImage();
//        DataBufferByte dataBuffer = (DataBufferByte) bufferedImage.getRaster().getDataBuffer();
//        byte[] pixels = dataBuffer.getData();
//        logger.info(pixels[0] + " " + pixels[1] + " " + pixels[2] + " " + pixels[3]);
//        Color color = new Color(bufferedImage.getRGB(0, 0));
//        logger.info(color.getRed()  + " " + color.getGreen()  + " " + color.getBlue() + " " + color.getAlpha());
//        logger.info(pixels[4] + " " + pixels[5] + " " + pixels[6] + " " + pixels[7]);
//        color = new Color(bufferedImage.getRGB(1, 0));
//        logger.info(color.getRed()  + " " + color.getGreen()  + " " + color.getBlue() + " " + color.getAlpha());
//        logger.info(pixels.length);
//        logger.info(bufferedImage.getHeight() + " * " + bufferedImage.getWidth() + " * 3" + " = " + (bufferedImage.getWidth() * bufferedImage.getHeight() * 4));
//        for(byte i: pixels) {
//            logger.info(i);
//        }
    }
}
