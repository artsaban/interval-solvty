package ru.nsc.interval.solvty.uns;

import ru.nsc.interval.solvty.IIntervalSolvtyGradient;
import ru.nsc.interval.solvty.IWithGradientEstimator;
import ru.nsc.interval.solvty.IntervalSolvtyGradient;

public class UnsSolvtyGradient implements IWithGradientEstimator {

    @Override
    public IIntervalSolvtyGradient getInstance() {
        return new IntervalSolvtyGradient(new UnsMeanValueEstimator());
    }
}
