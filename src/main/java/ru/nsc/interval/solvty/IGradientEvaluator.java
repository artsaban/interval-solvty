package ru.nsc.interval.solvty;

import gradient.Gradient;
import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;

public interface IGradientEvaluator {
    Gradient calc(SetInterval[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic);
}
