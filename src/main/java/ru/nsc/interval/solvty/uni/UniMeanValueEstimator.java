package ru.nsc.interval.solvty.uni;

import gradient.Gradient;
import gradient.GradientEvaluator;
import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.IGradientEvaluator;
import ru.nsc.interval.solvty.Utils;

public class UniMeanValueEstimator implements IGradientEvaluator {
    @Override
    public Gradient calc(SetInterval[] vars, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = b.length;
        int n = vars.length;

        GradientEvaluator ge = new GradientEvaluator(n, ic);
        Gradient[] x = ge.getVars(vars);

        Gradient sumOfMin, sumOfMax;
        Gradient hl, hr, tt;
        Gradient min = new Gradient(0, n, ic);

        for (int i = 0; i < m; i++) {
            sumOfMin = new Gradient(0, n, ic);
            sumOfMax = new Gradient(0, n, ic);

            for (int j = 0; j < n; j++) {
                hl = ge.mul(a[i][j].inf(), x[j]);
                hr = ge.mul(a[i][j].sup(), x[j]);

                sumOfMin = ge.add(
                    sumOfMin,
                    ge.min(hl, hr)
                );

                sumOfMax = ge.add(
                    sumOfMax,
                    ge.max(hl, hr)
                );
            }

            hl = ge.sub(
                b[i].mid(),
                sumOfMax
            );

            hr = ge.sub(
                sumOfMin,
                b[i].mid()
            );


            tt = ge.sub(
                b[i].rad(),
                ge.max(hl, hr, 0)
            );

            if (i == 0) {
                min = tt;
                continue;
            }

            min = ge.min(min, tt);
        }

        SetInterval[] centralPoint = new SetInterval[n];
        SetInterval[] bias = new SetInterval[n];
        UniEstimator te = new UniEstimator();

        for (int i = 0; i < n; i++) {
            centralPoint[i] = ic.numsToInterval(vars[i].mid(), vars[i].mid());
            bias[i] = ic.sub(vars[i], centralPoint[i]);
        }

        SetInterval fCentral = te.calcEstimation(centralPoint, a, b, ic);

        return new Gradient(
            ic.intersection(
                ic.add(fCentral, Utils.sclMul(min.dx, bias, ic)),
                min.x
            ),
            min.dx
        );
    }
}
