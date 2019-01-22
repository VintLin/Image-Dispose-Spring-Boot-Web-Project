package com.felink.service.dispose.effects.watermark;

import com.felink.service.configurer.Path;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class WordWatermarkUtils extends AbstractWatermarkUtils {
    private String word;

    public WordWatermarkUtils(String savePath, String ...contents) {
        super(savePath, contents);
        word = contents[0];
    }

    @Override
    public String disposeWatermark() {
        try {
            ;
            BufferedImage waterMark = makeWatermark();
            FileOutputStream outImgStream = new FileOutputStream(savePath);
            ImageIO.write(waterMark, "png", outImgStream);
            outImgStream.flush();
            outImgStream.close();
            return savePath;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    BufferedImage makeWatermark() {
        Font font = new Font("汉仪菱心体简", Font.PLAIN, 200);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(BG_700x400_PATH));
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        assert image != null;
        int width = image.getWidth();
        int height = image.getHeight();
        int fontWidth = font.getSize() * word.length();
        int fontHeight = font.getSize();
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        drawOutline(g, word, font, 10, (width - fontWidth) / 2, fontHeight);
        g.dispose();
        return bufferedImage;
    }
}
