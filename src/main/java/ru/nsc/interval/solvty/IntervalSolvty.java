package ru.nsc.interval.solvty;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import net.java.jinterval.rational.ExtendedRational;
import net.java.jinterval.rational.ExtendedRationalContext;
import optimization.ListItem;
import optimization.ListItemComparator;

import java.util.PriorityQueue;

public class IntervalSolvty implements IIntervalSolvty {
    private IEstimator estimator;
    private ISupergradientEvaluator sgEvaluator;

    public IntervalSolvty(IEstimator estimator, ISupergradientEvaluator sgEvaluator) {
        this.estimator = estimator;
        this.sgEvaluator = sgEvaluator;
    }

    @Override
    public PriorityQueue<ListItem> calc(
        SetInterval[] x,
        SetInterval[][] a,
        SetInterval[] b,
        ExtendedRational eps,
        SetIntervalContext ic,
        ExtendedRationalContext rc
    ) {
        PriorityQueue<ListItem> wList = new PriorityQueue<>(new ListItemComparator());
        SetInterval tmp = estimator.calcEstimation(x, a, b, ic);
        SetInterval[] box, gr;
        int index;

        wList.add(new ListItem(x, tmp, tmp.wid()));
        while (wList.peek().getEstimation().wid().gt(eps)) {
            box = wList.poll().getArgument();
//            index = basicChooseRule(box);
            gr = getRandomIntervalGradient(box, 3, a, b, ic);
//            gr = getEndpointsIntervalGradient(box, a, b, ic);
            index = altChooseRule(box, gr, ic, rc);
            initElem(box, index, a, b, ic, wList);
        }

        return wList;
    }

    private void initElem(
        SetInterval[] box,
        int index,
        SetInterval[][] a,
        SetInterval[] b,
        SetIntervalContext ic,
        PriorityQueue<ListItem> wList
    ) {
        SetInterval[] first = box.clone();
        SetInterval[] second = box.clone();
        first[index] = ic.numsToInterval(box[index].inf(), box[index].mid());
        second[index] = ic.numsToInterval(box[index].mid(), box[index].sup());
        SetInterval h1 = estimator.calcEstimation(first, a, b, ic);
        SetInterval h2 = estimator.calcEstimation(second, a, b, ic);
        wList.add(new ListItem(first, h1, h1.wid()));
        wList.add(new ListItem(second, h2, h2.wid()));
    }

    private int basicChooseRule(SetInterval[] box) {
        int max = 0;

        for (int i = 0; i < box.length; i++) {
            if (box[i].wid().gt(box[max].wid())) {
                max = i;
            }
        }

        return max;
    }

    private int altChooseRule(
        SetInterval[] box,
        SetInterval[] partials,
        SetIntervalContext ic,
        ExtendedRationalContext rc
    ) {
        if (partials != null) {
            int pMax = 0;
            for (int i = 0; i < partials.length; i++) {
                if (partials[i].wid().gt(partials[pMax].wid())) {
                    pMax = i;
                }
            }
            if (partials[pMax].wid().lt(ExtendedRational.POSITIVE_INFINITY)) {
                for (int i = 0; i < box.length; i++) {
                    if ((rc.mul(ic.abs(partials[i]).sup(), box[i].wid())).gt(rc.mul(ic.abs(partials[pMax]).sup(), box[pMax].wid()))) {
                        pMax = i;
                    }
                }
                return pMax;
            } else {
                return basicChooseRule(box);
            }
        } else {
            return basicChooseRule(box);
        }
    }

    private SetInterval[] getRandomIntervalGradient(
        SetInterval[] box,
        int number,
        SetInterval[][] a,
        SetInterval[] b,
        SetIntervalContext ic
    ) {
        double[] point;
        double[] mins = sgEvaluator.calc(Utils.getRandomPoint(box), a, b, ic);
        double[] maxs = mins.clone();
        SetInterval[] intervalGradient = new SetInterval[box.length];


        for (int i = 1; i < number; i++) {
            point = sgEvaluator.calc(Utils.getRandomPoint(box), a, b, ic);
            for (int j = 0; j < box.length; j++) {
                mins[j] = Double.min(mins[j], point[j]);
                maxs[j] = Double.max(maxs[j], point[j]);
            }
        }

        for (int i = 0; i < box.length; i++) {
            intervalGradient[i] = ic.numsToInterval(mins[i], maxs[i]);
        }

        return intervalGradient;
    }

    private SetInterval[] getEndpointsIntervalGradient(
        SetInterval[] box,
        SetInterval[][] a,
        SetInterval[] b,
        SetIntervalContext ic
    ) {
        double[][] points = Utils.getBoxEndpoints(box);
        double[] mins = sgEvaluator.calc(points[0], a, b, ic);
        double[] maxs = mins.clone();
        double[] point;
        SetInterval[] intervalGradient = new SetInterval[box.length];

        for (int i = 1; i < points.length; i++) {
            point = sgEvaluator.calc(points[i], a, b, ic);
            for (int j = 0; j < box.length; j++) {
                mins[j] = Double.min(mins[j], point[j]);
                maxs[j] = Double.max(maxs[j], point[j]);
            }
        }

        for (int i = 0; i < box.length; i++) {
            intervalGradient[i] = ic.numsToInterval(mins[i], maxs[i]);
        }

        return intervalGradient;
    }
}
