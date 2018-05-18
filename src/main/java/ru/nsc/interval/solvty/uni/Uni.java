package ru.nsc.interval.solvty.uni;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;

public class Uni {
    public static double calcUniValue(double[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        final int m = a.length;
        final int n = a[0].length;

        if (m != b.length || n != x.length) {
            throw new IllegalArgumentException();
        }

        SetInterval tmpI;
        double tmpD;
        double currentMin = Double.POSITIVE_INFINITY;

        for (int i = 0; i < m; i++) {
            tmpI = ic.numsToInterval(0, 0);

            for (int j = 0; j < n; j++) {
                tmpI = ic.add(tmpI, ic.mul(a[i][j], ic.numsToInterval(x[j], x[j])));
            }

            tmpD = b[i].doubleRad() - ic.sub(
                ic.numsToInterval(b[i].doubleMid(), b[i].doubleMid()),
                tmpI
            ).doubleMig();

            if (tmpD < currentMin) {
                currentMin = tmpD;
            }
        }

        return currentMin;
    }
}