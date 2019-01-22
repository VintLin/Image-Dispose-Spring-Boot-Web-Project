package com.felink.service.common.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author  WenTao Lin
 * @see     BasePoint
 * @see     BasePolygon
 * @see     BaseLine
 * @since 0.1
 *
 * */
public class BaseVector {
    private BasePoint from;
    private BasePoint to;
    private BasePoint originPoint;
    private BaseLine baseLine;
    private ArrayList<BaseVector> foregroundVectors;
    private ArrayList<BaseVector> backgroundVectors;
    private double length;

    public double getLength() { return length; }

    public BasePoint getFrom() { return from; }

    public void setFrom(BasePoint from) { this.from = from; }

    public BasePoint getTo() { return to; }

    public void setTo(BasePoint to) { this.to = to;}

    public BaseLine getBaseLine() { return baseLine; }

    public BaseVector(BasePoint from, BasePoint to) {
        this.from = from;
        this.to = to;
        originPoint = new BasePoint(to.x - from.x, to.y - from.y);
        length =  from.getLength(to);
        baseLine = new BaseLine(this);
    }

    /**
     * The distance from the point to the baseLine
     * @param point Calculate the distance from this point to baseLine
     * @return Distance from point to baseLine
     */
    public double getPointToVector(BasePoint point) {
        double cross = (to.x - from.x) * (point.x - from.x) +
                (to.y - from.y) * (point.y - from.y);
        double line = getSqrtLength();
        if(cross <= 0) {
            return from.getLength(point);
        }
        if(cross >= line) {
            return to.getLength(point);
        }
        double r = cross / line;
        double x = from.x + (to.x - from.x) * r;
        double y = from.y + (to.y - from.y) * r;
        return point.getLength(new BasePoint(x, y));
    }

    /**
     * Gets the Angle between two vectors
     * @param vector the other vector
     * @return Angle between two vectors
     */
    public double getAngle(BaseVector vector) {
        double productValue = (originPoint.x * vector.originPoint.x) + (originPoint.y * vector.originPoint.y);
        double cosValue = productValue / (vector.length * length);
        cosValue = cosValue < -1 ? -1 : cosValue;
        cosValue = cosValue > 1 ? 1 : cosValue;
        return getCrossProduct(vector.to) * (Math.acos(cosValue) * 180 / Math.PI);
    }

    public static double getAngle(BasePoint a, BasePoint b, BasePoint c) {
        return new BaseVector(b, a).getAngle(new BaseVector(b, c));
    }

    public List<BaseVector> getForegroundVectors() { return foregroundVectors;}

    public List<BaseVector> getBackgroundVectors() { return backgroundVectors;}

    public BaseVector getForegroundVector(int index) { return foregroundVectors.get(index); }

    public BaseVector getBackgroundVector(int index) { return backgroundVectors.get(index); }
    /**
     * Divide vectors into smaller segments
     * @param avg Number of segments divided
     */
    public void initChildVector(int avg) {
        foregroundVectors = new ArrayList<>();
        backgroundVectors = new ArrayList<>();
        switch(baseLine.tag) {
            case BaseLine.X_PARALLEL: {
                double step = (to.x - from.x) / avg;
                double foregroundBeginX = from.x;
                double backgroundBeginX = 2 * from.x - to.x;
                for(int i = 0; i < avg; i++) {
                    foregroundVectors.add(new BaseVector(from, new BasePoint(foregroundBeginX, from.y)));
                    backgroundVectors.add(new BaseVector(from, new BasePoint(backgroundBeginX, from.y)));
                    foregroundBeginX += step;
                    backgroundBeginX += step;
                }
            }break;
            case BaseLine.Y_PARALLEL: {
                double step = (to.y - from.y) / avg;
                double foregroundBeginY = from.y;
                double backgroundBeginY = 2 * from.y - to.y;
                for(int i = 0; i < avg; i++) {
                    foregroundVectors.add(new BaseVector(from, new BasePoint(from.x, foregroundBeginY)));
                    backgroundVectors.add(new BaseVector(from, new BasePoint(from.x, backgroundBeginY)));
                    foregroundBeginY += step;
                    backgroundBeginY += step;
                }
            }break;
            case BaseLine.LINE_EQUATION: {
                double step = (to.x - from.x) / avg;
                double foregroundBeginX = from.x;
                double backgroundBeginX = 2 * from.x - to.x;
                for(int i = 0; i < avg; i++){
                    foregroundVectors.add(new BaseVector(from, new BasePoint(foregroundBeginX, baseLine.getYByX(foregroundBeginX))));
                    backgroundVectors.add(new BaseVector(from, new BasePoint(backgroundBeginX, baseLine.getYByX(backgroundBeginX))));
                    foregroundBeginX += step;
                    backgroundBeginX += step;
                }
            }break;
        }
    }

    public boolean isCross(BasePoint from, BasePoint to) {
        return isCross(new BaseVector(from, to));
    }

    public boolean isCross(BaseVector vector) {
        BaseVector vector1 = new BaseVector(getFrom(), vector.getFrom());
        BaseVector vector2 = new BaseVector(getFrom(), vector.getTo());
        double result = crossProduct(vector1) * crossProduct(vector2);
        return result < 0;
    }

    public double crossProduct(BaseVector vector) {
        double x = getTo().x - getFrom().x;
        double y = getTo().y - getFrom().y;
        double x1 = vector.getTo().x - vector.getFrom().x;
        double y1 = vector.getTo().y - vector.getFrom().y;
        return x * y1 - x1 * y;
    }

    /**
     * The position of a point relative to a baseLine
     * @param point The point
     * @return -1 left 0 online 1 right
     */
    private int getCrossProduct(BasePoint point) {
        double cross = (point.x - from.x) * (to.y - from.y) - (to.x - from.x) * (point.y - from.y);
        return cross < 0 ? -1 : cross == 0 ? 0 : 1;
    }

    private double getSqrtLength() {
        return Math.pow((from.x - to.x), 2) + Math.pow((from.y - to.y), 2);
    }

    @Override
    public String toString() {
        return  "Vector: ("+ from.x + "," + from.y + ")->(" + to.x + "," + to.y + ")";
    }


    @Override
    public boolean equals(Object obj) {
        BaseVector vector = (BaseVector)obj;
        return vector.getFrom().equals(from) && vector.getTo().equals(to);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = result * 31 + from.hashCode();
        result = result * 31 + to.hashCode();
        return result;
    }
}
