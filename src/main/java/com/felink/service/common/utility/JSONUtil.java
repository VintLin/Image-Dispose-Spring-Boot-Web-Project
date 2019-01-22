package com.felink.service.common.utility;

import com.felink.project.model.ResponseJSON;
import com.felink.service.common.error.JSONContentNullException;
import com.felink.service.common.model.BasePoint;
import com.felink.service.common.model.BaseVector;
import com.felink.service.common.model.BasePolygon;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class JSONUtil {
    public static void checkJSON(String json) throws JSONContentNullException{
        if(json == null || json.equals(""))
            throw new JSONContentNullException("Param");
    }

    public static String getUserIdByJSON(JSONObject obj) throws JSONContentNullException {
        String uid = obj.getString("uid");
        if(uid == null || uid.equals("")) {
            throw new JSONContentNullException("Uid");
        }
        return uid;
    }

    public static String[] getArrayByTextArray(String textArray) {
        ArrayList<String> arrayList = new ArrayList<>();
        if (ResponseJSON.isJSONValid(textArray)) {
            JSONArray jsonArray = JSONArray.fromObject(textArray);
            for(int i = 0; i < jsonArray.size(); i++) {
                arrayList.add(jsonArray.getString(i));
            }
        } else {
            arrayList.addAll(Arrays.asList(textArray.split(",")));
        }
        String[] array = new String[arrayList.size()];
        return arrayList.toArray(array);
    }

    public static void getTransformByJSON(String transform, ArrayList<BaseVector> vectors, ArrayList<BasePolygon> polygons) throws JSONContentNullException {
        JSONArray jsonArray = JSONArray.fromObject(transform);
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject json = jsonArray.getJSONObject(i);
            JSONObject jsonVector = json.getJSONObject("vector");
            JSONArray jsonPoint1 = jsonVector.getJSONArray("begin");
            JSONArray jsonPoint2 = jsonVector.getJSONArray("end");
            BasePoint point1 = new BasePoint(jsonPoint1.getDouble(0), jsonPoint1.getDouble(1));
            BasePoint point2 = new BasePoint(jsonPoint2.getDouble(0), jsonPoint2.getDouble(1));
            BaseVector vector = new BaseVector(point1, point2);
            vectors.add(vector);
            JSONArray jsonPolygon = json.getJSONArray("scope");
            LinkedList<BasePoint> points = new LinkedList<>();
            for (int j = 0; j < jsonPolygon.size(); j++) {
                JSONArray jsonPoint = jsonPolygon.getJSONArray(j);
                BasePoint point = new BasePoint(jsonPoint.getDouble(0), jsonPoint.getDouble(1));
                points.add(point);
            }
            polygons.add(new BasePolygon(points));
        }
    }

    public static List<BaseVector> getVectorsByJSON(String vs) throws JSONContentNullException{
        JSONArray jsonVectors = JSONArray.fromObject(vs);
        if(jsonVectors == null) {
            throw new JSONContentNullException("Vector");
        }

        List<BaseVector> vectors = new ArrayList<>();
        for (int i = 0; i < jsonVectors.size(); i++) {
            JSONObject jsonVector= jsonVectors.getJSONObject(i);
            JSONArray jsonPoint1 = jsonVector.getJSONArray("begin");
            JSONArray jsonPoint2 = jsonVector.getJSONArray("end");
            BasePoint point1 = new BasePoint(Math.round(jsonPoint1.getDouble(0)), Math.round(jsonPoint1.getDouble(1)));
            BasePoint point2 = new BasePoint(Math.round(jsonPoint2.getDouble(0)), Math.round(jsonPoint2.getDouble(1)));
            BaseVector vector = new BaseVector(point1, point2);
            vectors.add(vector);
        }
        return vectors;
    }

    public static List<BasePoint> getPointsByJSON(String ps) throws JSONContentNullException {
        JSONArray jsonPoints = JSONArray.fromObject(ps);
        if(jsonPoints == null) {
            throw new JSONContentNullException("Points");
        }
        List<BasePoint> points = new ArrayList<>();
        for (int j = 0; j < jsonPoints.size(); j++) {
            JSONArray jsonPoint = jsonPoints.getJSONArray(j);
            BasePoint point = new BasePoint(Math.round(jsonPoint.getDouble(0)), Math.round(jsonPoint.getDouble(1)));
            points.add(point);
        }
        return points;
    }
}
