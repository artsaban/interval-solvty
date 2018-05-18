package ru.nsc.interval.solvty.uni;

import ru.nsc.interval.solvty.IIntervalSolvtyGradient;
import ru.nsc.interval.solvty.IWithGradientEstimator;
import ru.nsc.interval.solvty.IntervalSolvtyGradient;

public class UniSolvtyGradient implements IWithGradientEstimator {
    @Override
    public IIntervalSolvtyGradient getInstance() {
        return new IntervalSolvtyGradient(new UniMeanValueEstimator());
    }
}
