package ru.nsc.interval.solvty;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import net.java.jinterval.rational.ExtendedRational;
import net.java.jinterval.rational.ExtendedRationalContext;
import optimization.ListItem;

import java.util.PriorityQueue;

public interface IIntervalSolvty {
    PriorityQueue<ListItem> calc(
        SetInterval[] x,
        SetInterval[][] a,
        SetInterval[] b,
        ExtendedRational eps,
        SetIntervalContext ic,
        ExtendedRationalContext rc
    );
}
