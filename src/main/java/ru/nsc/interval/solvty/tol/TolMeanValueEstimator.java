package ru.nsc.interval.solvty.tol;

import gradient.Gradient;
import gradient.GradientEvaluator;
import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import ru.nsc.interval.solvty.IEstimator;
import ru.nsc.interval.solvty.Utils;

public class TolMeanValueEstimator implements IEstimator {
    @Override
    public SetInterval calcEstimation(SetInterval[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic) {
//        Gradient[] var = Gradient.init(x, ic);
//        int m = b.length;
//        int n = x.length;
//
//        Gradient sumOfMin, sumOfMax;
//        Gradient hl, hr, tt;
//        Gradient min = null;
//
//        for (int i = 0; i < m; i++) {
//            sumOfMin = Gradient.num(0);
//            sumOfMax = Gradient.num(0);
//
//            for (int j = 0; j < n; j++) {
//                hl = Gradient.num(a[i][j].inf()).mul(var[j]);
//                hr = Gradient.num(a[i][j].sup()).mul(var[j]);
//
//                sumOfMin = sumOfMin.add(((hl.neg()).max(hr.neg())).neg());
//                sumOfMax = sumOfMax.add(hl.max(hr));
//            }
//
//            hl = Gradient.num(b[i].mid()).sub(sumOfMax).abs();
//            hr = Gradient.num(b[i].mid()).sub(sumOfMin).abs();
//            tt = Gradient.num(b[i].rad()).sub(hl.max(hr));
//
//            if (i == 0) {
//                min = tt;
//                continue;
//            }
//
//            min = ((min.neg()).max(tt.neg())).neg();
//        }
//
//
//        SetInterval[] centralPoint = new SetInterval[x.length];
//        SetInterval[] bias = new SetInterval[x.length];
//        SetInterval fCentral = Tol.calc(Utils.getMid(x), a, b, ic);
//
//        for (int i = 0; i < x.length; i++) {
//            centralPoint[i] = ic.numsToInterval(x[i].mid(), x[i].mid());
//            bias[i] = ic.sub(x[i], centralPoint[i]);
//        }
//
//        assert min != null;
//        System.out.println(min.getX());
//        System.out.println(ic.add(fCentral, Utils.sclMul(min.getDX(), bias, ic)));
//        System.out.println("=============================");
//
//        return ic.add(fCentral, Utils.sclMul(min.getDX(), bias, ic));
        int m = b.length;
        int n = x.length;
        GradientEvaluator ge = new GradientEvaluator(n, ic);
        Gradient[] vars = ge.getVars(x);

        Gradient sumOfMin;
        Gradient sumOfMax;
        Gradient hl, hr, tt;
        Gradient min = null;

        for (int i = 0; i < m; i++) {
            sumOfMin = new Gradient(0, n, ic);
            sumOfMax = new Gradient(0, n, ic);

            for (int j = 0; j < n; j++) {
                hl = ge.mul(a[i][j].inf(), vars[j]);
                hr = ge.mul(a[i][j].sup(), vars[j]);

                sumOfMin = ge.add(sumOfMin, ge.min(hl, hr));
                sumOfMax = ge.add(sumOfMax, ge.max(hl, hr));
            }

            hl = ge.sub(b[i].mid(), sumOfMax);
            hr = ge.sub(b[i].mid(), sumOfMin);

            tt = ge.sub(b[i].rad(), ge.max(hl, hr));

            if (i == 0) {
                min = tt;
                continue;
            }

            min = ge.min(min, tt);
        }

        SetInterval[] centralPoint = new SetInterval[n];
        SetInterval[] bias = new SetInterval[n];

        for (int i = 0; i < n; i++) {
            centralPoint[i] = ic.numsToInterval(x[i].mid(), x[i].mid());
            bias[i] = ic.sub(x[i], centralPoint[i]);
        }

        SetInterval fCentral = Tol.calc(Utils.getMid(x), a, b, ic);

        assert min != null;
//        System.out.println(ic.add(fCentral, Utils.sclMul(min.dx, bias, ic)));
//        System.out.println(min.x);
//        System.out.println("===============================");

//        return ic.add(fCentral, Utils.sclMul(min.dx, bias, ic));
        return ic.intersection(ic.add(fCentral, Utils.sclMul(min.dx, bias, ic)), min.x);
    }
}
