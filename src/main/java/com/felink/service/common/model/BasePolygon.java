package com.felink.service.common.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * 多边形
 * 展示无用
 * @author linwentao
 */
public class BasePolygon extends LinkedList<BasePoint> implements Iterable<BasePoint> {
    private int size = 0;
    public double minX;
    public double minY;
    public double maxX;
    public double maxY;

    public BasePolygon() { }

    public BasePolygon(List<BasePoint> points) {
        super();
        addAll(points);
        getLimit();
    }

    public void addLast(BasePoint point) {
        if(!contains(point)) {
            super.addLast(point);
            addCompare(point);
        }
    }

    public void addFirst(BasePoint point) {
        if(!contains(point)) {
            super.addFirst(point);
            addCompare(point);
        }
    }

    public void add(int index, BasePoint point) {
        super.add(index, point);
        addCompare(point);
    }

    public void addAll(List<BasePoint> points) {
        super.addAll(points);
        getLimit();
    }

    private void addCompare(BasePoint p) {
        size = size();
        minX = minX < p.x ? minX: p.x;
        maxX = maxX > p.x ? maxX: p.x;
        minY = minY < p.y ? minY: p.y;
        maxY = maxY > p.y ? maxY: p.y;
    }

    private void getLimit() {
        if (isEmpty())
            return;
        size = size();
        double[] scope = BasePoint.getScope(this);
        minX = scope[0];
        minY = scope[1];
        maxX = scope[2];
        maxY = scope[3];
    }

    public boolean isClockwise() {
        int index = 0;
        double max = 0;
        for(int i = 0; i < size(); i++) {
            if(max < get(i).y) {
                max = get(i).y;
                index = i;
            }
        }
        int n = index + 1 == size()? 0: index + 1;
        int p = index == 0? size() - 1: index - 1;
        return get(n).x < get(p).x;
    }

    public boolean isOnline(int[] point) {
        return isOnline(new BasePoint(point[0], point[1]));
    }

    public boolean isOnline(BasePoint point) {
        for (int i = 0; i < size(); i++) {
            BasePoint p1 = get(i);
            BasePoint p2 = get((i + 1) % size());
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数

            // point 在p1p2 底部 --> 无交点
            if (point.y < Math.min(p1.y, p2.y))
                continue;
            // point 在p1p2 顶部 --> 无交点
            if (point.y > Math.max(p1.y, p2.y))
                continue;

            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.y == p2.y) {
                double minX = Math.min(p1.x, p2.x);
                double maxX = Math.max(p1.x, p2.x);
                // point在水平线段p1p2上,直接return true
                if ((point.y == p1.y) && (point.x >= minX && point.x <= maxX)) {
                    return true;
                }
            } else { // 求解交点
                double x = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
                if (x == point.x) // 当x=point.x时,说明point在p1p2线段上
                    return true;
            }
        }
        return false;
    }

    public boolean isInside(int[] point) {
        return isInside(new BasePoint(point[0], point[1]));
    }

    public boolean isInside(BasePoint point) {
        int nCross = 0;
        for (int i = 0; i < size(); i++) {
            BasePoint p1 = get(i);
            BasePoint p2 = get((i + 1) % size());
            // 取多边形任意一个边,做点point的水平延长线,求解与当前边的交点个数
            // p1p2是水平线段,要么没有交点,要么有无限个交点
            if (p1.y == p2.y)
                continue;
            // point 在p1p2 底部 --> 无交点
            if (point.y < Math.min(p1.y, p2.y))
                continue;
            // point 在p1p2 顶部 --> 无交点
            if (point.y >= Math.max(p1.y, p2.y))
                continue;
            // 求解 point点水平线与当前p1p2边的交点的 X 坐标
            double x = (point.y - p1.y) * (p2.x - p1.x) / (p2.y - p1.y) + p1.x;
            if (x > point.x) // 当x=point.x时,说明point在p1p2线段上
                nCross++; // 只统计单边交点
        }
        // 单边交点为偶数，点在多边形之外 ---
        return (nCross % 2 == 1);
    }

    public double getArea() {
        double area = 0.0;
        for(int i = 0; i < size; i++) {
            area += (get(i).x + get(i + 1).x) *
                    (get(i + 1).y - get(i).y);
        }
        area += (get(0).x + get(1).x) *
                (get(1).y - get(0).y);
        return 0.5 * Math.abs(area);
    }

    public List<BasePoint> getSamePoint(BasePolygon polygon) {
        List<BasePoint> points = new ArrayList<>();
        for(BasePoint point: this) {
            if(polygon.contains(point)){
                points.add(point);
                System.out.println(point);
            }
        }
        return points;
    }

    @Override
    public int hashCode(){
        int result = 17;
        for(BasePoint point: this) {
            result = result * 31 + point.hashCode();
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(BasePoint p: this) {
            sb.append(p).append("->");
        }
        return sb.toString();
    }
}
