package ru.nsc.interval.solvty.uni;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.ISupergradientEvaluator;

public class UniSupergradient implements ISupergradientEvaluator {
    @Override
    public double[] calc(double[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = a.length;
        int n = x.length;
        double f;

        // Вычисления внутри ортанта
        double[] tt = new double[m];
        double[] dl = new double[n];
        double[] ds = new double[n];
        double[] dz = new double[n];
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
            double azes = Double.POSITIVE_INFINITY;

            for (int j = 0; j < n; j++) {
                double dm = a[i][j].doubleInf();
                double dp = a[i][j].doubleSup();

                dl[j] = x[j] < 0 ? dm : dp;
                ds[j] = x[j] < 0 ? -dp : -dm;

                dz[j] = 0.5 * (dl[j] + ds[j]);

            }

            if (los <= 0 && 0 <= sus) {
                azes = 0;
            }

            double migs;

            double[] mins = new double[] {azes, alos, asus};
            double min = mins[0];
            int k = 0;
            for (int j = 1; j < 3; j++) {
                if (mins[j] < min) {
                    min = mins[j];
                    k = j;
                }
            }

            if (k == 0) {
                migs = azes;
                System.arraycopy(dz, 0, dd[i], 0, dz.length);
            } else if (k == 1) {
                migs = alos;
                System.arraycopy(dl, 0, dd[i], 0, dl.length);
            } else {
                migs = asus;
                System.arraycopy(ds, 0, dd[i], 0, ds.length);
            }

            tt[i] = b[i].doubleRad() - migs;
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
