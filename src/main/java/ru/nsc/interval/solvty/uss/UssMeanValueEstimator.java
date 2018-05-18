package ru.nsc.interval.solvty.uss;

import gradient.Gradient;
import gradient.GradientEvaluator;
import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.IGradientEvaluator;
import ru.nsc.interval.solvty.Utils;

public class UssMeanValueEstimator implements IGradientEvaluator {
    @Override
    public Gradient calc(SetInterval[] vars, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = b.length;
        int n = vars.length;

        GradientEvaluator ge = new GradientEvaluator(n, ic);
        Gradient s1, s2;
        Gradient min = new Gradient(0, n, ic), tt;
        Gradient[] x = ge.getVars(vars);

        for (int i = 0; i < m; i++) {
            s1 = new Gradient(0, n, ic);
            s2 = new Gradient(0, n, ic);

            for (int j = 0; j < n; j++) {
                s1 = ge.add(
                    s1,
                    ge.mul(
                        a[i][j].rad(),
                        ge.abs(x[j])
                    )
                );

                s2 = ge.add(
                    s2,
                    ge.mul(
                        a[i][j].mid(),
                        x[j]
                    )
                );
            }

            s2 = ge.abs(
                ge.sub(
                    b[i].mid(),
                    s2
                )
            );

            tt = ge.add(
                new Gradient(b[i].rad(), n, ic),
                ge.sub(s1, s2)
            );

            if (i == 0) {
                min = tt;
                continue;
            }

            min = ge.min(min, tt);
        }

        SetInterval[] centralPoint = new SetInterval[n];
        SetInterval[] bias = new SetInterval[n];
        UssEstimator ue = new UssEstimator();

        for (int i = 0; i < n; i++) {
            centralPoint[i] = ic.numsToInterval(vars[i].mid(), vars[i].mid());
            bias[i] = ic.sub(vars[i], centralPoint[i]);
        }

        SetInterval fCentral = ue.calcEstimation(centralPoint, a, b, ic);

        return new Gradient(
            ic.intersection(
                ic.add(fCentral, Utils.sclMul(min.dx, bias, ic)),
                min.x
            ),
            min.dx
        );
    }
}
