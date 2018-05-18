package ru.nsc.interval.solvty.uns;

import gradient.Gradient;
import gradient.GradientEvaluator;
import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.IGradientEvaluator;
import ru.nsc.interval.solvty.Utils;

public class UnsMeanValueEstimator implements IGradientEvaluator {
    @Override
    public Gradient calc(SetInterval[] vars, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = b.length;
        int n = vars.length;
        Gradient s1, s2;
        Gradient min = new Gradient(0, n, ic), tt;
        GradientEvaluator ge = new GradientEvaluator(n, ic);
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

            tt = ge.sub(
                s1,
                ge.max(
                    ge.sub(b[i].inf(), s2),
                    ge.sub(s2, b[i].sup()),
                    0
                )
            );

            if (i == 0) {
                min = tt;
                continue;
            }

            min = ge.min(min, tt);
        }

        SetInterval[] centralPoint = new SetInterval[n];
        SetInterval[] bias = new SetInterval[n];
        UnsEstimator ue = new UnsEstimator();

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
