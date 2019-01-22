package com.felink.service.dispose.effects.watermark;

import com.felink.service.configurer.Path;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class TwoSentencesWaterMarkUtils extends AbstractWatermarkUtils{

    static public void main(String[] args) {
//        TwoSentencesWaterMarkUtils waterMarkUtils = new TwoSentencesWaterMarkUtils();
//        waterMarkUtils.disposeWatermark("落霞与孤鹜齐飞", "秋水共长天一色", "1");
    }

    private String one;
    private String two;

    public TwoSentencesWaterMarkUtils(String savePath, String ...contents) {
        super(savePath, contents);
        one = contents[0];
        two = contents[1];
    }

    @Override
    public String disposeWatermark() {// 0 one 1 two 3 UUID
        try {
             // 待存储的地址
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
        Font font = new Font("汉仪雪君体繁", Font.PLAIN, 90);
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(BG_700x400_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert image != null;
        int width = image.getWidth();
        int height = image.getHeight();
        int fontHeight = font.getSize();
        int fontWidth = font.getSize() * ((one.length() > two.length()?
                one.length(): two.length()) + 1);

        BufferedImage bufferedImage = new BufferedImage(fontWidth, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.setColor(FONT_COLOR);
        g.setFont(font);
        g.drawString(one, 0, fontHeight);
        g.drawString(two, fontHeight / 2, fontHeight * 3);
        g.dispose();
        return bufferedImage;
    }


}
