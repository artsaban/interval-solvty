package gradient;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import net.java.jinterval.interval.set.SetIntervalContexts;
import net.java.jinterval.rational.ExtendedRational;

import java.util.Arrays;
import java.util.stream.Collectors;

public class GradientEvaluator {
    private int n;
    private SetIntervalContext ic;

    public GradientEvaluator(int n, SetIntervalContext ic) {
        this.n = n;
        this.ic = ic;
    }

    public Gradient[] getVars(SetInterval[] box) {
        Gradient[] vars = new Gradient[box.length];

        for (int i = 0; i < box.length; i++) {
            vars[i] = new Gradient(box[i], i, n, ic);
        }

        return vars;
    }

    public Gradient add(Gradient g1, Gradient g2) {
        SetInterval x = ic.add(g1.x, g2.x);
        SetInterval[] dx = new SetInterval[n];

        for (int i = 0; i < n; i++) {
            dx[i] = ic.add(g1.dx[i], g2.dx[i]);
        }

        return new Gradient(x, dx);
    }

    public Gradient sub(Gradient g1, Gradient g2) {
        SetInterval x = ic.sub(g1.x, g2.x);
        SetInterval[] dx = new SetInterval[n];

        for (int i = 0; i < dx.length; i++) {
            dx[i] = ic.sub(g1.dx[i], g2.dx[i]);
        }

        return new Gradient(x, dx);
    }

    public Gradient sub(ExtendedRational num, Gradient g2) {
        Gradient g1 = new Gradient(num, n, ic);
        return sub(g1, g2);
    }

    public Gradient mul(Gradient g1, Gradient g2) {
        SetInterval x = ic.mul(g1.x, g2.x);
        SetInterval[] dx = new SetInterval[n];

        for (int i = 0; i < dx.length; i++) {
            dx[i] = ic.add(
                ic.mul(g1.x, g2.dx[i]),
                ic.mul(g1.dx[i], g2.x)
            );
        }

        return new Gradient(x, dx);
    }

    public Gradient mul(ExtendedRational num, Gradient g2) {
        Gradient g1 = new Gradient(num, n, ic);
        return mul(g1, g2);
    }

    public Gradient neg(Gradient g1) {
        SetInterval x = ic.neg(g1.x);
        SetInterval[] dx = Arrays.stream(g1.dx)
            .map(it -> ic.neg(it))
            .toArray(SetInterval[]::new);
        return new Gradient(x, dx);
    }

    public Gradient min(Gradient g1, Gradient g2) {
        return neg(max(neg(g1), neg(g2)));
    }

    public Gradient max(Gradient g1, Gradient g2) {
        SetInterval x = ic.max(g1.x, g2.x);
        SetInterval[] dx = new SetInterval[n];

        if (g1.x.strictPrecedes(g2.x)) {
            System.arraycopy(g2.dx, 0, dx, 0, n);
        } else if (g2.x.strictPrecedes(g1.x)) {
            System.arraycopy(g1.dx, 0, dx, 0, n);
        } else {
            for (int i = 0; i < n; i++) {
                dx[i] = ic.convexHull(g1.dx[i], g2.dx[i]);
            }
        }

        return new Gradient(x, dx);
    }

    public Gradient abs(Gradient g1) {
        SetInterval x = ic.abs(g1.x);
        SetInterval[] dx = new SetInterval[n];
        SetInterval ZERO = ic.numsToInterval(0, 0);

        if (g1.x.strictPrecedes(ZERO)) {
            for (int i = 0; i < n; i++) {
                dx[i] = ic.neg(g1.dx[i]);
            }
        } else if (ZERO.strictPrecedes(g1.x)) {
            System.arraycopy(g1.dx, 0, dx, 0, n);
        } else {
            for (int i = 0; i < n; i++) {
                dx[i] = ic.convexHull(ic.neg(g1.dx[i]), g1.dx[i]);
            }
        }

        return new Gradient(x, dx);
    }
}
