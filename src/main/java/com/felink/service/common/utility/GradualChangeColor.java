package com.felink.service.common.utility;

import java.awt.*;

public class GradualChangeColor {
    private int choiceColor;
    private int R, G, B;
    private int step;
    private boolean flag = false;

    static public GradualChangeColor ChoiceColor(int choice){
        switch (choice) {
            case 0: {
                return new GradualChangeColor(new Color(235, 55, 55), 2, 1);
            }
            case 1: {
                return new GradualChangeColor(new Color(55, 235, 55), 3, 1);
            }
            case 2: {
                return new GradualChangeColor(new Color(55, 55, 235), 1, 1);
            }
            default: {
                return new GradualChangeColor(new Color(235, 55, 55), 2, 1);
            }
        }
    }

    public GradualChangeColor(int step) {
        this.step = step;
    }

    public GradualChangeColor(Color color, int choice, int step) {
        this.B = color.getBlue();
        this.G = color.getGreen();
        this.R = color.getRed();
        this.step = step;
        this.choiceColor = choice;
    }

    public Color getGradualChange() {
        switch (choiceColor) {
            case 0:{
                R += step;
                flag = R == 55 || R == 235;
            }break;
            case 1:{
                G += step;
                flag = G == 55 || G == 235;
            }break;
            case 2:{
                B += step;
                flag = B == 55 || B == 235;
            }break;
        }
        if(flag) {
            flag = false;
            step *= -1;
            choiceColor = choiceColor != 2 ? choiceColor + 1: 0;
        }
        return new Color(R, G, B);
    }

    public Color getFlicker() {
        int R = 255, G = 255, B = 255;

        if(step == 30 || step == 0) {
            flag = !flag;
        }

        if(flag) {
            step += -1;
        } else {
            step++;
        }
        return new Color(R - step, G - step, B - step * 3);
    }
}
