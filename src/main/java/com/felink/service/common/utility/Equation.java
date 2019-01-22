package com.felink.service.common.utility;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Equation {

    private double[][] savequot;// 保存变量系数
    private double[] constquot;// 保存常量系数
    private double[] saveResult;// 保存解的集合
    private double basic;

    private Equation(double quot[][]) {
        savequot = quot;
        basic = getMatrixResult(savequot);
    }

    private void setConstquot(double constquot[]) {
        this.constquot = constquot;
        saveResult = new double[]{0, 0, 0};
    }

    private double getMatrixResult(double input[][])// 递归的方法求得某个行列式的值
    {
        if (input.length == 2)//递归出口，为二阶行列式时，直接返回
        {
            return input[0][0] * input[1][1] - input[0][1] * input[1][0];
        } else {
            double[] temp = new double[input.length];//存放第一列的系数值
            double[][] tempinput = new double[input.length - 1][input.length - 1];
            double result = 0;
            for (int i = 0; i < input.length; i++) {
                temp[i] = input[i][0];
                int m = 0, n = 0;
                for (int k = 0; k < input.length; k++) {
                    if (k != i) {
                        for (m = 0; m < input.length - 1; m++) {
                            tempinput[n][m] = input[k][m + 1];//删除当前变量系数所在的行和列，得到减少一阶的新的行列式
                        }
                        n++;
                    }
                }
                if (i % 2 == 0)// 递归调用，利用代数余子式与相应系数变量的乘积之和得到多阶行列式的值
                {
                    result = result + temp[i] * getMatrixResult(tempinput);
                } else {
                    result = result - temp[i] * getMatrixResult(tempinput);
                }
            }
            return result;
        }
    }

    private double[][] getReplaceMatrix(int i)// 用常数系数替换相应的变量系数,得到新的行列式
    {
        double tempresult[][] = new double[savequot.length][savequot.length];
        for (int m = 0; m < savequot.length; m++) {
            for (int n = 0; n < savequot.length; n++) {
                if (i != m) {
                    tempresult[n][m] = savequot[n][m];
                } else {
                    tempresult[n][i] = constquot[n];// 用常量系数替换当前变量系数
                }
            }
        }
        return tempresult;
    }

    public double[] getResult() {
        if (Math.abs(basic) < 0.00001) {
            System.out.println("it dose not have the queue result!");
            return saveResult;
        }
        double[][] temp;
        for (int i = 0; i < saveResult.length; i++) {
            temp = getReplaceMatrix(i);
            saveResult[i] = getMatrixResult(temp) / basic;
        }
        return saveResult;
    }

    public static double[][] equation(double[][] coefficient, double[][] value) {
        Equation equation = new Equation(coefficient);
        double[][] result = new double[value.length][value.length];
        for(int i = 0; i < value.length; i++) {
            equation.setConstquot(value[i]);
            double[] res = equation.getResult();
            result[0][i] = res[0];
            result[1][i] = res[1];
            result[2][i] = res[2];
        }
        return result;
    }
}
