package ru.nsc.interval.solvty;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import org.apache.commons.lang3.StringUtils;

public class Utils {
    public static SetInterval[] getBox(String code, SetIntervalContext ic) {
        SetInterval[] box = new SetInterval[code.length()];

        for (int i = 0; i < code.length(); i++) {
            box[i] = code.charAt(i) == '0'
                ? ic.numsToInterval(-1000000, 0)
                : ic.numsToInterval(0, 1000000);
        }

        return box;
    }

    public static double[] getRandomPoint(SetInterval[] box) {
        double[] point = new double[box.length];

        for (int i = 0; i < point.length; i++) {
            point[i] = intoInterval(Math.random(), box[i].doubleInf(), box[i].doubleSup());
        }

        return point;
    }

    public static double[][] getBoxEndpoints(SetInterval[] box) {
        int n = 1 << box.length;
        double[][] points = new double[n][box.length];
        String code;

        for (int i = 0; i < n; i++) {
            code = StringUtils.leftPad(Integer.toBinaryString(i), box.length, '0');
            for (int j = 0; j < box.length; j++) {
                points[i][j] = code.charAt(j) == '0' ? box[j].doubleInf() : box[j].doubleSup();
            }
        }

        return points;
    }

    public static double[] getMid(SetInterval[] box) {
        double[] out = new double[box.length];
        for (int i = 0; i < box.length; i++) {
            out[i] = box[i].doubleMid();
        }
        return out;
    }

    public static SetInterval sclMul(SetInterval[] box1, SetInterval[] box2, SetIntervalContext ic) {
        SetInterval result = ic.numsToInterval(0, 0);
        for (int i = 0; i < box1.length; i++) {
            result = ic.add(result, ic.mul(box1[i], box2[i]));
        }
        return result;
    }

    private static double intoInterval(double point, double inf, double sup) {
        return  inf + (sup - inf) * point;
    }
}
