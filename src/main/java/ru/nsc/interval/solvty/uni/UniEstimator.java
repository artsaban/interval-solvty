package ru.nsc.interval.solvty.uni;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.IEstimator;

public class UniEstimator implements IEstimator {
    @Override
    public SetInterval calcEstimation(SetInterval[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = b.length;
        int n = x.length;

        SetInterval sumOfMin, sumOfMax;
        SetInterval hl, hr, tt;
        SetInterval min = null;

        for (int i = 0; i < m; i++) {
            sumOfMin = ic.numsToInterval(0, 0);
            sumOfMax = ic.numsToInterval(0, 0);

            for (int j = 0; j < n; j++) {
                hl = ic.mul(ic.numsToInterval(a[i][j].inf(), a[i][j].inf()), x[j]);
                hr = ic.mul(ic.numsToInterval(a[i][j].sup(), a[i][j].sup()), x[j]);

                sumOfMin = ic.add(
                    sumOfMin,
                    ic.min(hl, hr)
                );
                sumOfMax = ic.add(
                    sumOfMax,
                    ic.max(hl, hr)
                );
            }

            hl = ic.sub(
                ic.numsToInterval(b[i].mid(), b[i].mid()),
                sumOfMax
            );

            hr = ic.sub(
                sumOfMin,
                ic.numsToInterval(b[i].mid(), b[i].mid())
            );


            tt = ic.sub(
                ic.numsToInterval(b[i].rad(), b[i].rad()),
                ic.max(hl, hr, ic.numsToInterval(0, 0))
            );

            if (i == 0) {
                min = tt;
                continue;
            }

            min = ic.min(min, tt);
        }

        return min;
    }
}
