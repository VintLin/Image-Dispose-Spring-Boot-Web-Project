package com.felink.service.dispose.effects.watermark;

import com.felink.service.configurer.Path;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public abstract class AbstractWatermarkUtils {
    String BG_400x200_PATH =  Path.BACKGROUND_PATH + "400x200background.png";
    String BG_200x400_PATH = Path.BACKGROUND_PATH + "200x400background.png";
    String BG_700x400_PATH = Path.BACKGROUND_PATH + "700x400background.png";
    String NAME_EFFECTS_BACKGROUND_PATH = Path.BACKGROUND_PATH + "effects_background_1.png";

    Color FONT_COLOR = new Color(255,255,255,255);

    protected String[] contents;
    protected String savePath;

    AbstractWatermarkUtils(String savePath, String ...contents) {
        this.contents = contents;
        this.savePath = savePath;
    }

    abstract String disposeWatermark();

    abstract BufferedImage makeWatermark();

    Graphics2D drawOutline(Graphics2D g, String text, Font font, int lineWeight, int x, int y) {
        FontRenderContext oneContext = g.getFontRenderContext();
        TextLayout oneLayout = new TextLayout(text, font, oneContext);
        Shape shape = oneLayout.getOutline(AffineTransform.getTranslateInstance(x, y));
        g.setColor(Color.white);
        g.setStroke(new BasicStroke(lineWeight));
        g.draw(shape);
        return g;
    }

    protected BufferedImage markOn(String backgroundPath, String watermarkPath) {
        try {
            BufferedImage background = ImageIO.read(new File(backgroundPath));
            BufferedImage watermark = ImageIO.read(new File(watermarkPath));
            return markOn(background, watermark);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    BufferedImage markOn(String backgroundPath, BufferedImage watermark) {
        try {
            BufferedImage background = ImageIO.read(new File(backgroundPath));
            return markOn(background, watermark);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    BufferedImage markOn(BufferedImage background, String watermarkPath) {
        try {
            BufferedImage watermark = ImageIO.read(new File(watermarkPath));
            return markOn(background, watermark);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    BufferedImage markOn(BufferedImage background, BufferedImage watermark) {
        BufferedImage bufferedImage = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        g.drawImage(background, 0, 0, background.getWidth(), background.getHeight(), null);
        g.drawImage(watermark,
                (background.getWidth() - watermark.getWidth()) / 2,
                (background.getHeight() - watermark.getHeight()) / 2,
                watermark.getWidth(), watermark.getHeight(), null);
        g.dispose();
        return bufferedImage;
    }

    int getWatermarkLength(String waterMarkContent, Graphics2D g) {
        return g.getFontMetrics(g.getFont()).charsWidth(waterMarkContent.toCharArray(), 0, waterMarkContent.length());
    }
}
