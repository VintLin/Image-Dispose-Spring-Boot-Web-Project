package com.felink.service.dispose.transitions.transitions;

import com.felink.service.common.model.BaseImage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class AbstractTransitions {
    Logger log = LoggerFactory.getLogger(AbstractTransitions.class);
    BaseImage baseImage;
    BufferedImage endImage;
    int index;
    int frame = 48;

    AbstractTransitions(String inputFile, String outputPath, int index) {
        this(inputFile, null, outputPath, index);
    }

    AbstractTransitions(String inputFile1, String inputFile2, String outputPath, int index) {
        try {
            this.index = index;
            baseImage = new BaseImage(inputFile1, outputPath);
            if(inputFile2 == null) {
                endImage = new BufferedImage(baseImage.getWidth(), baseImage.getHeight(), BufferedImage.TYPE_INT_RGB);
            } else {
                endImage = ImageIO.read(new File(inputFile2));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract int doTransform();

    abstract void makeImage();

    void saveImage(int index) {
        baseImage.saveImageWithoutReset(index);
    }
}
