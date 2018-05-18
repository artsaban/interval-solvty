package ru.nsc.interval.solvty;

import gradient.Gradient;
import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import net.java.jinterval.rational.ExtendedRational;
import net.java.jinterval.rational.ExtendedRationalContext;
import optimization.ListItemComparator;
import optimization.ListItemGradient;

import java.util.PriorityQueue;

public class IntervalSolvtyGradient implements IIntervalSolvtyGradient {
    private IGradientEvaluator gradientEvaluator;

    public IntervalSolvtyGradient(IGradientEvaluator ge) {
        this.gradientEvaluator = ge;
    }

    @Override
    public PriorityQueue<ListItemGradient> calc(
        SetInterval[] x,
        SetInterval[][] a,
        SetInterval[] b,
        ExtendedRational eps,
        SetIntervalContext ic,
        ExtendedRationalContext rc
    ) {
        PriorityQueue<ListItemGradient> wList = new PriorityQueue<>(new ListItemComparator());
        Gradient tmp = gradientEvaluator.calc(x, a, b, ic);
        ListItemGradient item;
        int index;

        wList.add(new ListItemGradient(x, tmp.x, tmp.x.wid(), tmp.dx));
        while (wList.peek().getEstimation().wid().gt(eps)) {
            item = wList.poll();
            index = altChooseRule(item.getArgument(), item.getPartials(), ic, rc);
            initElem(item.getArgument(), index, a, b, ic, wList);
        }

        return wList;
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

    private void initElem(
        SetInterval[] box,
        int index,
        SetInterval[][] a,
        SetInterval[] b,
        SetIntervalContext ic,
        PriorityQueue<ListItemGradient> wList
    ) {
        SetInterval[] first = box.clone();
        SetInterval[] second = box.clone();
        first[index] = ic.numsToInterval(box[index].inf(), box[index].mid());
        second[index] = ic.numsToInterval(box[index].mid(), box[index].sup());
        Gradient h1 = gradientEvaluator.calc(first, a, b, ic);
        Gradient h2 = gradientEvaluator.calc(second, a, b, ic);
        wList.add(new ListItemGradient(first, h1.x, h1.x.wid(), h1.dx));
        wList.add(new ListItemGradient(second, h2.x, h2.x.wid(), h1.dx));
    }
}
