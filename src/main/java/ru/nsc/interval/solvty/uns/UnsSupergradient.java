package ru.nsc.interval.solvty.uns;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.ISupergradientEvaluator;

import java.util.Arrays;

public class UnsSupergradient implements ISupergradientEvaluator {
    @Override
    public double[] calc(double[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
        int m = a.length;
        int n = x.length;
        double f;

        // Вычисления внутри ортанта
        double[] tt = new double[m];
        double s1;
        double s2;
        SetInterval s;
        double[][] dd = new double[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill(dd[i], 0);
        }

        for (int i = 0; i < m; i++) {
            s1 = 0;
            s2 = 0;
            for (int j = 0; j < n; j++) {
                s1 = s1 + a[i][j].doubleRad()*Math.abs(x[j]);
                dd[i][j] = x[j]<0 ? -a[i][j].doubleRad() : a[i][j].doubleRad();

                s2 += a[i][j].doubleMid()*x[j];
            }

            s = ic.sub(b[i], ic.numsToInterval(s2, s2));
            tt[i] = s1 - s.doubleMig();

            double los = s.doubleInf();
            double sus = s.doubleSup();
            double alos = Math.abs(los);
            double asus = Math.abs(sus);
            double azes = Double.POSITIVE_INFINITY;

            if (los <= 0 && 0 <= sus) {
                azes = 0;
            }

            double[] mins = new double[] {azes, alos, asus};
            double min = mins[0];
            int k = 0;
            for (int j = 1; j < 3; j++) {
                if (mins[j] < min) {
                    min = mins[j];
                    k = j;
                }
            }

            for (int j = 0; j < n; j++) {
                if (b[i].doubleRad()==0) {
                    dd[i][j] += s.doubleSup()>0 ? a[i][j].doubleMid() : -a[i][j].doubleMid();
                }
                else {
                    if (k == 1) {
                        dd[i][j] += a[i][j].doubleMid();
                    } else if (k == 2) {
                        dd[i][j] -= a[i][j].doubleMid();
                    }
                }
            }
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
