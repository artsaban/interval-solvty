import gradient.Gradient;
import gradient.GradientEvaluator;
import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import net.java.jinterval.interval.set.SetIntervalContexts;
import net.java.jinterval.rational.ExtendedRational;
import net.java.jinterval.rational.ExtendedRationalContext;
import net.java.jinterval.rational.ExtendedRationalContexts;
import optimization.ListItem;
import ru.nsc.interval.solvty.Utils;
import ru.nsc.interval.solvty.tol.Tol;
import ru.nsc.interval.solvty.tol.TolEstimator;
import ru.nsc.interval.solvty.tol.TolMeanValueEstimator;
import ru.nsc.interval.solvty.tol.TolSolvty;
import ru.nsc.interval.solvty.uni.UniSolvty;
import ru.nsc.interval.solvty.uns.UnsSolvty;
import ru.nsc.interval.solvty.uss.UssSolvty;

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
            ic.numsToInterval(-1000000, 1000000),
            ic.numsToInterval(-1000000, 1000000),
            ic.numsToInterval(-1000000, 1000000),
            ic.numsToInterval(-1000000, 1000000),
            ic.numsToInterval(-1000000, 1000000),
            ic.numsToInterval(-1000000, 1000000),
            ic.numsToInterval(-1000000, 1000000),
        };

        ExtendedRational eps = ExtendedRational.valueOf(1.e-2);

        TolSolvty tolSolvty = new TolSolvty();
        UniSolvty uniSolvty = new UniSolvty();
        UssSolvty ussSolvty = new UssSolvty();
        UnsSolvty unsSolvty = new UnsSolvty();

        double startTime = System.currentTimeMillis();
        PriorityQueue<ListItem> foo = unsSolvty.getInstance().calc(x, a, b, eps, ic, rc);
        double endTime = System.currentTimeMillis();
        System.out.println((endTime - startTime) / 1000);
        System.out.println(foo.peek());
    }
}
