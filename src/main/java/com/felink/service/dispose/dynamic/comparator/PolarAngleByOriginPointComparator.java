package com.felink.service.dispose.dynamic.comparator;

import com.felink.service.common.model.BasePoint;
import com.felink.service.common.model.BaseVector;

import java.util.Comparator;

/**
 * 用于BasePoint排序
 * 构造函数设置一个点,之后通过比较容器中各个点与穿过该点的x轴水平线的夹角排序
 * @author linwentao
 */
public class PolarAngleByOriginPointComparator implements Comparator<BasePoint> {
    private BasePoint originPoint;
    public PolarAngleByOriginPointComparator(BasePoint oPoint) {
        originPoint = oPoint;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int compare(BasePoint o1, BasePoint o2) {
        BaseVector v0, v1, v2;
        v0 = new BaseVector(originPoint, o1);
        v1 = new BaseVector(originPoint, o2);
        v2 = new BaseVector(originPoint, new BasePoint(originPoint.x + 10, originPoint.y));
        double angle0 = v2.getAngle(v0);
        double angle1 = v2.getAngle(v1);
//        System.out.println(o1 +""+ angle0 + "\n" + o2 +""+ angle1 + "\n");
        return Double.compare(angle0, angle1);
    }
}
