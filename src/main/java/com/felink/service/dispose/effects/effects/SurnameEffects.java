package com.felink.service.dispose.effects.effects;

import com.felink.service.common.model.BasePoint;
import com.felink.service.common.utility.GradualChangeColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SurnameEffects extends  AbstractEffects{

    public SurnameEffects(String inputPath, String savePath) {
        super(inputPath, savePath);
    }

    public void doEffects() {
        BasePoint point = new BasePoint(image.getWidth(), image.getHeight());
        double len = point.getLength(new BasePoint(0, 0));
        GradualChangeColor tool = new GradualChangeColor(new Color(235, 55, 55), 2, 5);
        int frame = 150;
        for (int i = 0; i < frame; i++) {
            transformImage(image.getOperateImage(), tool.getGradualChange(), len);
            image.saveImage(i);
        }
    }

    private void transformImage(BufferedImage image, Color color, double len) {
        BasePoint point = new BasePoint(image.getWidth(), image.getHeight());
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if(image.getRGB(x, y) != 0xff000000) {
                    double l = point.getLength(new BasePoint(x, y)) / len;
                    l = l > 0.6? l: 0.6;
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
