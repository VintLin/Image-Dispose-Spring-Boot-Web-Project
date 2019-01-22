package com.felink.service.dispose.transitions.transitions;

import com.felink.service.common.model.BasePoint;

public class CircleTransitions extends AbstractTransitions{
    private int r;
    private BasePoint originPoint;

    CircleTransitions(String inputFile, String outputPath, int index) {
        super(inputFile, null, outputPath, index);
    }

    public CircleTransitions(String inputFile1, String inputFile2, String outputPath, int index) {
        super(inputFile1, inputFile2, outputPath, index);
    }

    @Override
    public int doTransform() {
        int MaxLen = (baseImage.getWidth() > baseImage.getHeight()? baseImage.getWidth(): baseImage.getHeight());
        int step = (MaxLen + MaxLen / 9) / (frame * 2);
        r = 0;
        originPoint = new BasePoint(baseImage.getWidth() >> 1, baseImage.getHeight() >> 1);
        for(int i = index; i < index + frame; i++) {
            r += step;
            makeImage();
            saveImage(i);
        }
        return index + frame;
    }

    @Override
    void makeImage() {
        int minX = (int)originPoint.x - r;
        minX = minX > 0? minX: 0;
        int minY = (int)originPoint.y - r;
        minY = minY > 0? minY: 0;
        int maxX = (int)originPoint.x + r;
        maxX = maxX > baseImage.getWidth()? baseImage.getWidth(): maxX;
        int maxY = (int)originPoint.y + r;
        maxY = maxY > baseImage.getHeight()? baseImage.getHeight(): maxY;
        for(int x = minX; x < maxX; x++) {
            for(int y = minY; y < maxY; y++) {
               if(originPoint.getLength(new BasePoint(x, y)) < r ) {
                    baseImage.getOperateImage().setRGB(x, y, endImage.getRGB(x, y));
               }
            }
        }
    }

}
