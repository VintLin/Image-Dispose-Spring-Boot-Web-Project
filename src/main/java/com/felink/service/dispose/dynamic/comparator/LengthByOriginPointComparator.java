package com.felink.service.dispose.dynamic.comparator;

import com.felink.service.common.model.BasePoint;

import java.util.Comparator;

/**
 * 用于BasePoint的排序
 * 构造函数设置一个点,之后通过比较容器中各个点到该点的距离进行排序
 * @author linwentao
 */
public class LengthByOriginPointComparator implements Comparator<BasePoint> {

    private BasePoint point;
    public LengthByOriginPointComparator(BasePoint point) {
        this.point = point;
    }

    @Override
    public int compare(BasePoint o1, BasePoint o2) {
        double len1 = point.getLength(o1);
        double len2 = point.getLength(o2);
        return Double.compare(len1, len2);
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}