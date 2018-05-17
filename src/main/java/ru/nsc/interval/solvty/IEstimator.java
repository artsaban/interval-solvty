package ru.nsc.interval.solvty;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;

public interface IEstimator {
    SetInterval calcEstimation(SetInterval[] x, SetInterval[][] a, SetInterval[] b, SetIntervalContext ic);
}
