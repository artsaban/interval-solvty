package ru.nsc.interval.solvty.uns;

import ru.nsc.interval.solvty.IIntervalSolvty;
import ru.nsc.interval.solvty.IWithEstimator;
import ru.nsc.interval.solvty.IntervalSolvty;
import ru.nsc.interval.solvty.uni.UniGradient;

public class UnsSolvty implements IWithEstimator {

    @Override
    public IIntervalSolvty getInstance() {
        return new IntervalSolvty(
            new UnsEstimator(),
            new UnsGradient()
        );
    }

}
