package ru.nsc.interval.solvty.uss;

import ru.nsc.interval.solvty.IIntervalSolvtyGradient;
import ru.nsc.interval.solvty.IWithGradientEstimator;
import ru.nsc.interval.solvty.IntervalSolvtyGradient;

public class UssSolvtyGradient implements IWithGradientEstimator {
    @Override
    public IIntervalSolvtyGradient getInstance() {
        return new IntervalSolvtyGradient(new UssMeanValueEstimator());
    }
}
