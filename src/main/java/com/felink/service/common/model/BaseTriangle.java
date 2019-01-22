package com.felink.service.common.model;


import com.felink.service.dispose.dynamic.comparator.TriangleVertexComparator;

import java.util.*;

/**
 * 三角形
 */
public class BaseTriangle extends ArrayList<BasePoint> implements Iterable<BasePoint>{

    public BaseTriangle(BasePoint ...points) {
        addAll(Arrays.asList(points));
    }

    private void sort() {
        sort(new TriangleVertexComparator());
    }

    public boolean replace(BasePoint old, BasePoint newPoint) {
        if(contains(old)){
            set(indexOf(old), newPoint);
            return true;
        }
        return false;
    }

    public List<BasePoint> getVertex() {
        List<BasePoint> points = new ArrayList<>();
        for(BasePoint point: this) {
            points.add(point.clone());
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
    public BaseTriangle clone() {
        BaseTriangle triangle = new BaseTriangle();
        for(BasePoint point: this){
            triangle.add(point.clone());
        }
        return triangle;
    }

    @Override
    public String toString() {
        return get(0) + " " + get(1) + " " + get(2);
    }
}
