package ru.nsc.interval.solvty.uns;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;

public class Uns {
    public static double value(double[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int n = x.length;
        int m = a.length;
        double h, f, s1, min = Double.POSITIVE_INFINITY;
        SetInterval s2;

        for (int i = 0; i < m; i++) {
            s1 = 0;
            s2 = ic.neg(b[i]);

            for (int j = 0; j < n; j++) {
                s1 += a[i][j].doubleRad() * Math.abs(x[j]);
                h = a[i][j].doubleMid() * x[j];
                s2 = ic.add(s2, ic.numsToInterval(h, h));
            }

            f = s1 - s2.doubleMig();

            if (f < min) {
                min = f;
            }
        }

        return min;
    }
}