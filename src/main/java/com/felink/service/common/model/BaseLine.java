package com.felink.service.common.model;

/**
 * 用于计算直线在直角坐标系中的表达式
 * @author linwentao
 * @see com.felink.service.common.utility.ScanningLine
 */
public class BaseLine {
    public static final int X_PARALLEL = 0x01;
    public static final int Y_PARALLEL = 0x02;
    public static final int LINE_EQUATION = 0x03;

    public int tag;
    private double k;
    private double b;
    public double x;
    public double y;

    /**
     * 通过两点计算直线的表达式
     * @param one point
     * @param two point
     */
    public BaseLine(BasePoint one,BasePoint two) { getEquation(one, two); }

    /**
     * 通过向量计算直线表达式
     * @param vector 向量
     */
    public BaseLine(BaseVector vector) {
        getEquation(vector.getTo(), vector.getFrom());
    }

    /**
     * 通过x坐标值与表达式计算y坐标值
     * @param x x坐标值
     * @return y坐标值
     */
    public double getYByX(double x) {
        switch (tag) {
            case X_PARALLEL:{
                return 0;
            }
            case Y_PARALLEL:{
                return y;
            }
            case LINE_EQUATION:{
                return k * x + b;
            }
            default:{
                return k * x + b;
            }
        }
    }

    /**
     * 通过y坐标值与表达式计算x坐标值
     * @param y y坐标值
     * @return x坐标值
     */
    public double getXByY(double y) {
        switch (tag) {
            case X_PARALLEL:{
                return x;
            }
            case Y_PARALLEL:{
                return 0;
            }
            case LINE_EQUATION:{
                return (y - b) / k;
            }
            default:{
                return (y - b) / k;
            }
        }
    }

    /**
     * 计算直线表达式
     * @param one point
     * @param two point
     */
    private void getEquation(BasePoint one, BasePoint two) {
        if(two.x == one.x) {
            tag = Y_PARALLEL;
            x = two.x;
        } else if(two.y == one.y) {
            tag = X_PARALLEL;
            y = two.y;
        } else {
            tag = LINE_EQUATION;
            k = (two.y - one.y) / (two.x - one.x);
            b = (one.y - k * one.x);
        }
    }

    /**
     * 输入一个点判断其是否在直线下方,若直线垂直则判断是否在右边
     * @param point point
     * @return isBottom
     */
    public int isBottom(BasePoint point) {
        switch (tag) {
            case X_PARALLEL: {
                return Double.compare(y, point.y);
            }
            case Y_PARALLEL: {
                return Double.compare(point.x, x);
            }
            case LINE_EQUATION: {
                double y = point.x * k + b;
                return Double.compare(y, point.y);
            }
            default: {
                return -1;
            }
        }
    }

}
