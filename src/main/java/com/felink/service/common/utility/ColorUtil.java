package com.felink.service.common.utility;

import java.awt.*;

public class ColorUtil {

    public static int getScreenColor(int oneRgb, int twoRgb){
        Color oneColor = new Color(oneRgb);
        Color twoColor = new Color(twoRgb);
        oneColor = getComplementaryColor(oneColor);
        twoColor = getComplementaryColor(twoColor);
        int red, green, blue;
        red = 0xff - oneColor.getRed() * twoColor.getRed() / 0xff;
        green = 0xff - oneColor.getGreen() * twoColor.getGreen() / 0xff;
        blue = 0xff - oneColor.getBlue() * twoColor.getBlue() / 0xff;
        return new Color(red, green, blue).getRGB();
    }

    public static Color getComplementaryColor(Color color){
        int red, green, blue;
        red = 0xff - color.getRed();
        green = 0xff - color.getGreen();
        blue = 0xff - color.getBlue();
        return new Color(red, green, blue);
    }
}
