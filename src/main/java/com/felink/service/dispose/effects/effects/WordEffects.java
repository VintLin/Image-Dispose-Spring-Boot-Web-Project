package com.felink.service.dispose.effects.effects;

import com.felink.service.common.utility.GradualChangeColor;

import java.awt.*;
import java.awt.image.BufferedImage;

public class WordEffects extends AbstractEffects {

    public WordEffects(String inputPath, String savePath) {
        super(inputPath, savePath);
    }

    @Override
    public void doEffects() {
        int frame = 300;
        GradualChangeColor tool = new GradualChangeColor(1);
        for (int i = 0; i < frame; i++) {
            transformImage(image.getOperateImage(), tool.getFlicker());
            image.saveImage(i);
        }
    }

    private void transformImage(BufferedImage image, Color color) {
        for (int x = 0; x < image.getWidth(); x++) {
            for (int y = 0; y < image.getHeight(); y++) {
                if(image.getRGB(x, y) != 0x00000000) {
                    image.setRGB(x, y, color.getRGB());
                }
            }
        }
    }
}
