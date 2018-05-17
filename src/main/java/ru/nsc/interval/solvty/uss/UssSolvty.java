package ru.nsc.interval.solvty.uss;

import ru.nsc.interval.solvty.IIntervalSolvty;
import ru.nsc.interval.solvty.IWithEstimator;
import ru.nsc.interval.solvty.IntervalSolvty;

public class UssSolvty implements IWithEstimator {

    @Override
    public IIntervalSolvty getInstance() {
        return new IntervalSolvty(new UssEstimator(), null);
    }

}
