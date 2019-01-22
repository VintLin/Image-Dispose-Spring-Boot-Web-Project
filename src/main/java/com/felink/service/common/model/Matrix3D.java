package com.felink.service.common.model;

import com.felink.service.common.utility.Equation;

public class Matrix3D {
    private double maxCol = 3,maxRow = 3;
    private double[][] matrix = new double[3][3];

    public Matrix3D(BaseVector vector, BasePoint finalPoint1, BasePoint finalPoint2) {
        this(new BaseVector(vector.getTo(), vector.getFrom()),
                new BaseVector(finalPoint1, finalPoint1),
                new BaseVector(finalPoint2, finalPoint2));
    }

    public Matrix3D(BaseTriangle originTriangle, BaseTriangle motionTriangle) {
        this(new BaseVector(motionTriangle.get(0), originTriangle.get(0)),
                new BaseVector(motionTriangle.get(1), originTriangle.get(1)),
                new BaseVector(motionTriangle.get(2), originTriangle.get(2)));
    }

    public Matrix3D(BaseVector ...vectors) {
        double[][] col = new double[vectors.length][];
        double[][] values = new double[vectors.length][vectors.length];
        for(int i = 0; i < vectors.length; i++) {
            col[i] = new double[]{vectors[i].getFrom().x, vectors[i].getFrom().y, 1};
            values[0][i] = vectors[i].getTo().x;
            values[1][i] = vectors[i].getTo().y;
            values[2][i] = 1;
        }
        matrix = Equation.equation(col, values);
    }

    public double[] transform(double x, double y) {
        return new double[]{x * matrix[0][0] + y * matrix[1][0] + 1 * matrix[2][0],
                x * matrix[0][1] + y * matrix[1][1] + 1 * matrix[2][1]};
    }

    public BasePoint transform(BasePoint p){
        double[] poi = transform(p.x, p.y);
        return new BasePoint(poi[0], poi[1]);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        for(double[] row: matrix) {
            sb.append("|");
            for(double item: row) {
                sb.append("\t").append(item).append("\t");
            }
            sb.append("|\n");
        }
        return sb.toString();
    }
}
