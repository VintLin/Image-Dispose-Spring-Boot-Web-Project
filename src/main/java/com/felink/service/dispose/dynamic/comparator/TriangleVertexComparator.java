package com.felink.service.dispose.dynamic.comparator;

import com.felink.service.common.model.BasePoint;

import java.util.Comparator;
import java.util.List;

/**
 * 用于BasePoint的排序
 * y坐标越大, x坐标越小排序越靠前
 * @author linwentao
 * @see com.felink.service.common.utility.ScanningLine
 */
public class TriangleVertexComparator implements Comparator<BasePoint> {

    @Override
    public int compare(BasePoint o1, BasePoint o2) {
        if(o2.y > o1.y){
            return 1;
        } else if(o1.y == o2.y) {
            return Double.compare(o1.x, o2.x);
        } else {
            return -1;
        }
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}
