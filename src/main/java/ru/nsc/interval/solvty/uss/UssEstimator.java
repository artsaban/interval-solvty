package ru.nsc.interval.solvty.uss;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.IEstimator;

public class UssEstimator implements IEstimator {

    @Override
    public SetInterval calcEstimation(SetInterval[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = b.length;
        int n = x.length;
        SetInterval s1, s2;
        SetInterval min = null, tt;

        for (int i = 0; i < m; i++) {
            s1 = ic.numsToInterval(0, 0);
            s2 = ic.numsToInterval(0, 0);

            for (int j = 0; j < n; j++) {
                s1 = ic.add(
                    s1,
                    ic.mul(
                        ic.numsToInterval(a[i][j].rad(), a[i][j].rad()),
                        ic.abs(x[j])
                    )
                );

                s2 = ic.add(
                    s2,
                    ic.mul(
                        ic.numsToInterval(a[i][j].mid(), a[i][j].mid()),
                        x[j]
                    )
                );
            }

            s2 = ic.abs(
                ic.sub(
                    ic.numsToInterval(b[i].mid(), b[i].mid()),
                    s2
                )
            );

            tt = ic.add(
                ic.numsToInterval(b[i].rad(), b[i].rad()),
                ic.sub(s1, s2)
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
