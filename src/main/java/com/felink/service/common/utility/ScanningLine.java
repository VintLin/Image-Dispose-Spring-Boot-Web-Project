package com.felink.service.common.utility;

import com.felink.service.common.model.BaseLine;
import com.felink.service.common.model.BasePoint;
import com.felink.service.dispose.dynamic.comparator.TriangleVertexComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 扫描线
 * @author linwentao
 * @see com.felink.service.dispose.dynamic.model.DynamicFragment
 */
public class ScanningLine {
    private Set<int[]> intList = new HashSet<>();

    public ScanningLine(List<BasePoint> vertex) {
        drawTriangle(vertex);
    }

    public Set<int[]> getIndex() {
        return intList;
    }

    private void drawTriangle(List<BasePoint> vertex) {
        vertex.sort(new TriangleVertexComparator());
        if(vertex.get(1).y == vertex.get(0).y) {
            //平顶
            drawFlatTopTri(vertex);
        } else if (vertex.get(1).y == vertex.get(2).y) {
            //平底
            drawFlatButtomdTri(vertex);
        } else {
            //其他 三角形 拆分
            BaseLine line = new BaseLine(vertex.get(0), vertex.get(2));
            BasePoint point;
            if(line.tag == BaseLine.Y_PARALLEL){
                point = new BasePoint(line.x, vertex.get(1).y);
            } else {
                point = new BasePoint(line.getXByY(vertex.get(1).y), vertex.get(1).y);
            }
            List<BasePoint> boardTriangle = new ArrayList<>();
            boardTriangle.add(point);
            boardTriangle.add(vertex.get(0));
            boardTriangle.add(vertex.get(1));
            boardTriangle.sort(new TriangleVertexComparator());
            drawFlatButtomdTri(boardTriangle);

            List<BasePoint> belowTriangle = new ArrayList<>();
            belowTriangle.add(point);
            belowTriangle.add(vertex.get(1));
            belowTriangle.add(vertex.get(2));
            belowTriangle.sort(new TriangleVertexComparator());
            drawFlatTopTri(belowTriangle);
        }
    }

    private void drawFlatTopTri(List<BasePoint> points)
    {
        double dy = points.get(0).y - points.get(2).y;
        double dxLeft = (points.get(0).x - points.get(2).x) / dy;
        double dxRight = (points.get(1).x - points.get(2).x) / dy;
        double start, end;
        start = end = points.get(2).x;
        for(int i = (int)points.get(2).y; i < points.get(0).y; i++) {
            DrawLine((int)start, (int)end, i);
            dy = (i + 1) - points.get(2).y;
            start = points.get(2).x + dy * dxLeft;
            end = points.get(2).x + dy * dxRight;
        }
    }

    private void drawFlatButtomdTri(List<BasePoint> points)
    {
        double dy = points.get(0).y - points.get(1).y;
        double dxl = (points.get(0).x - points.get(1).x) / dy;
        double dxr = (points.get(0).x - points.get(2).x) / dy;
        double start, end;
        start = points.get(1).x;
        end = points.get(2) .x;
        for(int i = (int)points.get(1).y; i < points.get(0).y; i++) {
            DrawLine((int)start, (int)end, i);
            dy = (i + 1) - points.get(1).y;
            start = points.get(1).x + dy * dxl;
            end = points.get(2) .x + dy * dxr;
        }
    }

    private void DrawLine(int start, int end, int y)
    {
        if(start != end)
            intList.add(new int[]{y, start, end});
    }
}
