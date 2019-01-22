package com.felink.service.dispose.dynamic.dynamic;

import com.felink.service.common.model.BaseVector;
import com.felink.service.dispose.dynamic.delaunay.Pnt;
import com.felink.service.dispose.dynamic.delaunay.Triangle;
import com.felink.service.dispose.dynamic.delaunay.Triangulation;
import com.felink.service.common.model.BasePoint;
import com.felink.service.common.model.BaseTriangle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

public class DelaunayPlane {
    private static Logger logger = LoggerFactory.getLogger(DelaunayPlane.class);
    private Triangulation triangulation;

    /**
     * 生成Delaunay剖分三角
     * @param ps 平面上散点
     * @param w 平面宽
     * @param h 平面长
     */
    DelaunayPlane(List<BasePoint> ps, int w, int h) {
        Triangle initialTriangle = new Triangle(
                new Pnt(w, h),
                new Pnt(0, h),
                new Pnt(0, 0));
        Triangle recentTriangle = new Triangle(
                new Pnt(w, h),
                new Pnt(w, 0),
                new Pnt(0, 0));
        triangulation = new Triangulation(initialTriangle, recentTriangle);
        for(BasePoint p: ps) {
            triangulation.delaunayPlace(new Pnt(p.x, p.y));
        }
    }

    /**
     * 往剖分三角中加散点
     * @param point 点
     */
    void addPoint(BasePoint point) {
        triangulation.delaunayPlace(new Pnt(point.x, point.y));
    }

    /**
     * 获取对应的三角
     * @param vectors 移动路径
     * @param fixTriangles 固定的三角区域
     * @param motionTriangles 形变的三角区域
     */
    void getFixAndMotionTriangles(List<BaseVector> vectors, Set<BaseTriangle> fixTriangles, Set<BaseTriangle> motionTriangles) {
        boolean isMotion = false;
        for(Triangle triangle: triangulation) {
            for(BaseVector vector: vectors) {
                if(triangle.contains(vector.getFrom().toPnt())) {
                    isMotion = true;
                    break;
                }
            }
            if(isMotion) {
                motionTriangles.add(triangle.toBaseTriangle());
                isMotion = false;
            }else {
                fixTriangles.add(triangle.toBaseTriangle());
            }
        }
    }
}
