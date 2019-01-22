package com.felink.service.dispose.effects.watermark;

import com.felink.service.configurer.Path;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SurnameWatermarkUtils extends AbstractWatermarkUtils {
    private String surname;
    private String content;

    public SurnameWatermarkUtils(String savePath, String... contents) {
        super(savePath, contents);
        surname = contents[0];
        if(contents.length < 2) {
            content = "纤歌凝而白云遏";
        } else {
            content = contents[1];
        }
    }


    @Override
    public String disposeWatermark() {
        try {
            // Path.WATER_SURNAME_PATH + token + ".png" 待存储的地址
            BufferedImage waterMark = makeWatermark();
            BufferedImage image = markOn(NAME_EFFECTS_BACKGROUND_PATH, waterMark);
            FileOutputStream outImgStream = new FileOutputStream(savePath);
            ImageIO.write(image, "png", outImgStream);
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
        Font surnameFont = new Font("微软雅黑", Font.PLAIN, 140); // 水印字体
        Font contentFont = new Font("微软雅黑", Font.PLAIN, 30); // 水印字体
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(BG_400x200_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 文件转化为图片
        assert image != null;
        int width = image.getWidth();
        int height = image.getHeight();
        int contentHeight = contentFont.getSize();
        int wordHeight = surnameFont.getSize();

        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(image, 0, 0, width, height, null);
        g.setColor(FONT_COLOR); //根据图片的背景设置水印颜色
        g.setFont(surnameFont); //设置字体
        g.drawString(surname, (width - getWatermarkLength(surname, g)) / 2, wordHeight);
        g.setColor(new Color(0,0,0,255));
        g.fillRect((width - getWatermarkLength(content, g)) / 2, height / 2 - contentHeight + 5,
                      getWatermarkLength(content, g), contentHeight);
        g.setColor(FONT_COLOR);
        g.setFont(contentFont);
        g.drawString(content, (width - getWatermarkLength(content, g)) / 2, height / 2);
        g.dispose(); // 输出图片

        for(int x = 0; x < width; x++) {
            for(int y = 0; y < height; y++) {
                if(bufferedImage.getRGB(x,y) == 0xff000000) {
                    bufferedImage.setRGB(x, y, 0x00000000);
                }
            }
        }
        return bufferedImage;
    }

}
