package com.felink.service.dispose.effects.effects;

import com.felink.service.common.model.BasePoint;
import com.felink.service.common.utility.GradualChangeColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class TwoSentencesEffects extends AbstractEffects{

    public TwoSentencesEffects(String inputPath, String savePath) {
        super(inputPath, savePath);
    }

    @Override
    public void doEffects() {
        BasePoint point = new BasePoint(image.getWidth(), image.getHeight());
        double len = point.getLength(new BasePoint(0, 0));
        int frame = 300;
        GradualChangeColor tool = new GradualChangeColor(new Color(235, 55, 55), 2, 5);
        for (int i = 0; i < frame; i++) {
            transformImage(image.getOperateImage(), tool.getGradualChange(), len);
            image.saveImage(i);
        }
    }

    private void transformImage(BufferedImage image, Color color, double len) {
        BasePoint point = new BasePoint(image.getWidth(), image.getHeight());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if(image.getRGB(x, y) != 0x00000000) {
                    double l = point.getLength(new BasePoint(x, y)) / len;
                    l = l > 0.6? l: 0.6;
                    image.getRaster();
                    image.setRGB(x, y, new Color(
                            (int)(color.getRed() * l),
                            (int)(color.getGreen() * l),
                            (int)(color.getBlue() * l)).getRGB());
                }
            }
        }
    }

    public static void main(String[] args) {

    }
}
