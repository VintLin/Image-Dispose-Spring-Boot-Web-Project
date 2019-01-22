package com.felink.service.dispose.transitions.transitions;

public class RectangleTransitions extends AbstractTransitions {
    private int  minX, minY, maxX, maxY;

    RectangleTransitions(String inputFile, String outputPath, int index) {
        super(inputFile, null, outputPath, index);
    }

    public RectangleTransitions(String inputFile1, String inputFile2, String outputPath, int index) {
        super(inputFile1, inputFile2, outputPath, index);
    }

    @Override
    public int doTransform() {
        int stepX = baseImage.getWidth() / frame;
        int stepY = baseImage.getHeight() / frame;
        int lenX = 0, lenY = 0;
        for(int i = index; i < index + frame; i++) {
            lenX += stepX;
            lenY += stepY;
            minX = (baseImage.getWidth() - lenX) / 2;
            maxX = baseImage.getWidth() - minX;
            minY = (baseImage.getHeight() - lenY) / 2;
            maxY = baseImage.getHeight() - minY;
            makeImage();
            saveImage(i);
        }
        return index + frame;
    }

    @Override
    void makeImage() {
        for(int x = minX; x < maxX; x++) {
            for(int y = minY; y < maxY; y++) {
                baseImage.getOperateImage().setRGB(x, y, endImage.getRGB(x, y));
            }
        }
    }

    public static void main(String[] args) {

    }
}
