import gradient.Gradient;
import gradient.GradientEvaluator;
import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import net.java.jinterval.interval.set.SetIntervalContexts;
import net.java.jinterval.rational.ExtendedRational;
import net.java.jinterval.rational.ExtendedRationalContext;
import net.java.jinterval.rational.ExtendedRationalContexts;
import optimization.ListItem;
import optimization.ListItemGradient;
import ru.nsc.interval.solvty.Utils;
import ru.nsc.interval.solvty.tol.*;
import ru.nsc.interval.solvty.uni.Uni;
import ru.nsc.interval.solvty.uni.UniEstimator;
import ru.nsc.interval.solvty.uni.UniSolvty;
import ru.nsc.interval.solvty.uni.UniSolvtyGradient;
import ru.nsc.interval.solvty.uns.Uns;
import ru.nsc.interval.solvty.uns.UnsEstimator;
import ru.nsc.interval.solvty.uns.UnsSolvty;
import ru.nsc.interval.solvty.uns.UnsSolvtyGradient;
import ru.nsc.interval.solvty.uss.UssEstimator;
import ru.nsc.interval.solvty.uss.UssMeanValueEstimator;
import ru.nsc.interval.solvty.uss.UssSolvty;
import ru.nsc.interval.solvty.uss.UssSolvtyGradient;

import java.util.Arrays;
import java.util.PriorityQueue;

public class Main {
    public static void main(String[] args) {
        SetIntervalContext ic = SetIntervalContexts.getAccur64();
        ExtendedRationalContext rc = ExtendedRationalContexts.exact();

//        SetInterval[][] a = {
//            {ic.numsToInterval(2, 3), ic.numsToInterval(-1, 2)},
//            {ic.numsToInterval(1, 2), ic.numsToInterval(1, 3)},
//            {ic.numsToInterval(-1, 1), ic.numsToInterval(0, 1)}
//        };
//
//        SetInterval[] b = {
//            ic.numsToInterval(0, 60),
//            ic.numsToInterval(10, 72),
//            ic.numsToInterval(-10, 36)
//        };

//        SetInterval[][] a = {
//            {ic.numsToInterval(2, 4), ic.numsToInterval(-2 , 1)},
//            {ic.numsToInterval(-1, 2), ic.numsToInterval(2, 4)}
//        };
//
//        SetInterval[] b = {
//                ic.numsToInterval(-2, 2),
//                ic.numsToInterval(-2, 2)
//        };

//        SetInterval[][] a = {
//            {ic.numsToInterval(3, 3), ic.numsToInterval(1, 2)},
//            {ic.numsToInterval(1, 2), ic.numsToInterval(3, 3)}
//        };
//
//        SetInterval[] b = {
//            ic.numsToInterval(5, 7),
//            ic.numsToInterval(7, 9)
//        };

        SetInterval[][] a = {
            {ic.numsToInterval(4,6), ic.numsToInterval(-9,0), ic.numsToInterval(0,12), ic.numsToInterval(2,3), ic.numsToInterval(5,9), ic.numsToInterval(-23,-9), ic.numsToInterval(15,23)},
            {ic.numsToInterval(0,1), ic.numsToInterval(6,10), ic.numsToInterval(-1,1), ic.numsToInterval(-1,3), ic.numsToInterval(-5,1), ic.numsToInterval(1,15), ic.numsToInterval(-3,1)},
            {ic.numsToInterval(0,3), ic.numsToInterval(-20,-9), ic.numsToInterval(12,77), ic.numsToInterval(-6,30), ic.numsToInterval(0,3), ic.numsToInterval(-18,1), ic.numsToInterval(0,1)},
            {ic.numsToInterval(-4,1), ic.numsToInterval(-1,1), ic.numsToInterval(-3,1), ic.numsToInterval(3,5), ic.numsToInterval(5,9), ic.numsToInterval(1,2), ic.numsToInterval(1,4)},
            {ic.numsToInterval(0,3), ic.numsToInterval(0,6), ic.numsToInterval(0,20), ic.numsToInterval(-1,5), ic.numsToInterval(8,14), ic.numsToInterval(-6,1), ic.numsToInterval(10,17)},
            {ic.numsToInterval(-7,-2), ic.numsToInterval(1,2), ic.numsToInterval(7,14), ic.numsToInterval(-3,1), ic.numsToInterval(0,2), ic.numsToInterval(3,5), ic.numsToInterval(-2,1)},
            {ic.numsToInterval(-1,5), ic.numsToInterval(-3,2), ic.numsToInterval(0,8), ic.numsToInterval(1,11), ic.numsToInterval(-5,10), ic.numsToInterval(2,7), ic.numsToInterval(6,82)}
        };

        SetInterval[] b = {
            ic.numsToInterval(-10,95),
            ic.numsToInterval(14,35),
            ic.numsToInterval(-6,2),
            ic.numsToInterval(7,30),
            ic.numsToInterval(4,95),
            ic.numsToInterval(-6,46),
            ic.numsToInterval(-2,65)
        };

        SetInterval[] x = {
            ic.numsToInterval(-1000001, -1000000),
            ic.numsToInterval(-1000001, -1000000),
            ic.numsToInterval(-1000001, -1000000),
            ic.numsToInterval(-1000001, -1000000),
            ic.numsToInterval(-1000001, -1000000),
            ic.numsToInterval(-1000001, -1000000),
            ic.numsToInterval(-1000001, -1000000),
        };

        ExtendedRational eps = ExtendedRational.valueOf(1.e-1);

        TolSolvty tolSolvty = new TolSolvty();
        UniSolvty uniSolvty = new UniSolvty();
        UssSolvty ussSolvty = new UssSolvty();
        UnsSolvty unsSolvty = new UnsSolvty();

        TolSolvtyGradient tolSolvtyGradient = new TolSolvtyGradient();
        UniSolvtyGradient uniSolvtyGradient = new UniSolvtyGradient();
        UssSolvtyGradient ussSolvtyGradient = new UssSolvtyGradient();
        UnsSolvtyGradient unsSolvtyGradient = new UnsSolvtyGradient();

        long startTime = System.nanoTime();

//        PriorityQueue<ListItem> foo = ussSolvty.getInstance().calc(x, a, b, eps, ic, rc);
//        System.out.println(foo.peek());

//        PriorityQueue<ListItemGradient> bar = ussSolvtyGradient.getInstance().calc(x, a, b, eps, ic, rc);
//        System.out.println(bar.peek());

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;
        System.out.println(totalTime / 1000000000.0);
    }
}
