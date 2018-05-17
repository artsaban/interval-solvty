package ru.nsc.interval.solvty.uni;

import ru.nsc.interval.solvty.IIntervalSolvty;
import ru.nsc.interval.solvty.IWithEstimator;
import ru.nsc.interval.solvty.IntervalSolvty;

public class UniSolvty implements IWithEstimator {

    @Override
    public IIntervalSolvty getInstance() {
        return new IntervalSolvty(
            new UniEstimator(),
            new UniGradient()
        );
    }

}
