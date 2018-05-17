package ru.nsc.interval.solvty.tol;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.ISupergradientEvaluator;

public class TolGradient implements ISupergradientEvaluator {
    @Override
    public double[] calc(double[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = a.length;
        int n = x.length;
        double f;


        // Вычисления внутри ортанта
        double[] tt = new double[m];
        double[] dl = new double[n];
        double[] ds = new double[n];
        double[][] dd = new double[m][n];

        for (int i = 0; i < m; i++) {
            SetInterval rowByColumn = ic.numsToInterval(0, 0);

            for (int j = 0; j < n; j++) {
                rowByColumn = ic.add(
                    rowByColumn,
                    ic.mul(
                        a[i][j],
                        ic.numsToInterval(x[j], x[j])
                    )
                );
            }

            SetInterval s = ic.sub(
                ic.numsToInterval(b[i].doubleMid(), b[i].doubleMid()),
                rowByColumn
            );

            double los = s.doubleInf();
            double sus = s.doubleSup();
            double alos = Math.abs(los);
            double asus = Math.abs(sus);

            for (int j = 0; j < n; j++) {
                double dm = a[i][j].doubleInf();
                double dp = a[i][j].doubleSup();

                dl[j] = Math.signum(los) * (x[j] < 0 ? dm : dp);
                ds[j] = Math.signum(sus) * (x[j] < 0 ? dp : dm);
            }

            double mags;

            if (alos > asus) {
                mags = alos;
                System.arraycopy(dl, 0, dd[i], 0, dl.length);
            } else {
                mags = asus;
                System.arraycopy(ds, 0, dd[i], 0, ds.length);
            }

            tt[i] = b[i].doubleRad() - mags;

        }

        f = Double.POSITIVE_INFINITY;
        int mc = 0;

        for (int i = 0; i < m; i++) {
            if (tt[i] < f) {
                f = tt[i];
                mc = i;
            }
        }

        return dd[mc];
    }
}
