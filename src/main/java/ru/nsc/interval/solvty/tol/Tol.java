package ru.nsc.interval.solvty.tol;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;

public class Tol {
    public static SetInterval calc(double[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = b.length;
        int n = x.length;

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
            ).doubleMag();

            if (tmpD < currentMin) {
                currentMin = tmpD;
            }
        }

        return ic.numsToInterval(currentMin, currentMin);
    }
}
