package com.felink.service.common.model;

import com.felink.service.dispose.dynamic.delaunay.Pnt;

import java.util.List;

/**
 * 二维坐标点
 * @author linwentao
 * @see BasePolygon
 * @see BaseTriangle
 * @see BaseVector
 */
public class BasePoint {
    public double x;
    public double y;

    public BasePoint(double x, double y){
        this.x = x;
        this.y = y;
    }

    /**
     * 判断两点连线长度
     * @param point point
     * @return length
     */
    public double getLength(BasePoint point) {
        return Math.sqrt(Math.pow((x - point.x), 2) + Math.pow((y - point.y), 2));
    }

    /**
     * 将BasePoint转为Pnt实例
     * @return pnt
     */
    public Pnt toPnt() {
        return new Pnt(x, y);
    }

    /**
     * 比较多点得出最大x,y 以及最小x,y
     * @param ps points
     * @return {minX, minY, maxX, maxY}
     */
    static double[] getScope(List<BasePoint> ps) {
        double maxX=0, maxY=0, minX=0, minY=0;
        for(BasePoint p : ps) {
            minX = minX < p.x ? minX: p.x;
            maxX = maxX > p.x ? maxX: p.x;
            minY = minY < p.y ? minY: p.y;
            maxY = maxY > p.y ? maxY: p.y;
        }
        return new double[]{minX, minY, maxX, maxY};
    }

    @Override
    public BasePoint clone() {
        return new BasePoint(x, y);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = (int) (result * 31 + x);
        result = (int) (result * 31 + y);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == this) {
            return true;
        }
        if(!(obj instanceof BasePoint)) {
            return false;
        }
        BasePoint point = (BasePoint)obj;
        return point.x == x && point.y == y;
    }

    @Override
    public String toString() {
        return String.format(" (%f, %f) ", x, y);
    }
}

