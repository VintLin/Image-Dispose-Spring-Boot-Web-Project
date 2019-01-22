package com.felink.service.dispose.transitions.transitions;

import com.felink.service.common.model.BasePoint;

public class MultiCircleTransitions extends AbstractTransitions {
    private int r;
    private BasePoint p1, p2, p3, p4;

    MultiCircleTransitions(String inputFile, String outputPath, int index) {
        super(inputFile, null, outputPath, index);
    }

    public MultiCircleTransitions(String inputFile1, String inputFile2, String outputPath, int index) {
        super(inputFile1, inputFile2, outputPath, index);
    }

    @Override
    public int doTransform() {
        int MaxLen = (baseImage.getWidth() > baseImage.getHeight()? baseImage.getWidth(): baseImage.getHeight());
        int step = (MaxLen + MaxLen / 9) / (frame * 2);
        r = 0;
        p1 = new BasePoint(baseImage.getWidth() * 0.3, baseImage.getHeight() * 0.2);
        p2 = new BasePoint(baseImage.getWidth() * 0.75, baseImage.getHeight() * 0.75);
        p3 = new BasePoint(baseImage.getWidth() * 0.1, baseImage.getHeight() * 0.6);
        p4 = new BasePoint(baseImage.getWidth() * 0.2, baseImage.getHeight() * 0.9);

        for(int i = index; i < index + frame; i++) {
            r += step;
            makeImage();
            saveImage(i);
        }
        return index + frame;
    }

    @Override
    void makeImage() {
        for(int x = 0; x < baseImage.getWidth(); x++) {
            for(int y = 0; y < baseImage.getHeight(); y++) {
                if(p1.getLength(new BasePoint(x, y)) < r || p2.getLength(new BasePoint(x, y)) < (r * 0.7)
                        || p3.getLength(new BasePoint(x, y)) < (r * 0.5) || p4.getLength(new BasePoint(x, y)) < (r * 0.4)) {
                    baseImage.getOperateImage().setRGB(x, y, endImage.getRGB(x, y));
                }
            }
        }
    }

//    public static void main(String[] args) {
//        LinkedList<String> images = new LinkedList<>();
//        images.add(Path.ALBUM_IMAGE_PATH + "2.jpg");
//        images.add(Path.ALBUM_IMAGE_PATH + "3.jpg");
//        images.add(Path.ALBUM_IMAGE_PATH + "4.jpg");
//        images.add(Path.ALBUM_IMAGE_PATH + "5.jpg");
//        int[] trans = {TransitionsFactory.CIRCLE, TransitionsFactory.MULTICIRCLE, TransitionsFactory.RECTANGLE};
//        String inputPath = TransitionsFactory.doTransitions(images, trans, Path.ALBUM_TEMP_PATH);
//        Executor.getInstance().imageToVideo(inputPath, Path.ALBUM_TEMP_PATH + "3.MP4", null, 24);
//    }
}
