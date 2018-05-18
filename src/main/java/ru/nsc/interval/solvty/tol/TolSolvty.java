package ru.nsc.interval.solvty.tol;

import ru.nsc.interval.solvty.IIntervalSolvty;
import ru.nsc.interval.solvty.IWithEstimator;
import ru.nsc.interval.solvty.IntervalSolvty;

public class TolSolvty implements IWithEstimator {

    @Override
    public IIntervalSolvty getInstance() {
        return new IntervalSolvty(
            new TolEstimator(),
            new TolSupergradient()
        );
    }

}
