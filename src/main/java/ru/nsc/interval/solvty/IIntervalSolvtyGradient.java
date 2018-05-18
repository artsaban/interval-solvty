package ru.nsc.interval.solvty;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import net.java.jinterval.rational.ExtendedRational;
import net.java.jinterval.rational.ExtendedRationalContext;
import optimization.ListItemGradient;

import java.util.PriorityQueue;

public interface IIntervalSolvtyGradient {
    PriorityQueue<ListItemGradient> calc(
        SetInterval[] x,
        SetInterval[][] a,
        SetInterval[] b,
        ExtendedRational eps,
        SetIntervalContext ic,
        ExtendedRationalContext rc
    );
}
