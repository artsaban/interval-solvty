package ru.nsc.interval.solvty.tol;

import ru.nsc.interval.solvty.IWithGradientEstimator;
import ru.nsc.interval.solvty.IntervalSolvtyGradient;

public class TolSolvtyGradient implements IWithGradientEstimator {
    @Override
    public IntervalSolvtyGradient getInstance() {
        return new IntervalSolvtyGradient(new TolMeanValueEstimator());
    }
}
