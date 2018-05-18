package gradient;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.interval.set.SetIntervalContext;
import net.java.jinterval.rational.ExtendedRational;

import java.util.Arrays;

public class Gradient {
    public SetInterval x;
    public SetInterval[] dx;

    Gradient(SetInterval x, int coordinate, int length, SetIntervalContext ic) {
        SetInterval[] dx = new SetInterval[length];
        Arrays.fill(dx, ic.numsToInterval(0, 0));
        this.x = x;
        dx[coordinate] = ic.numsToInterval(1, 1);
        this.dx = dx;
    }

    public Gradient(SetInterval x, SetInterval[] dx) {
        this.x = x;
        this.dx = dx;
    }

    public Gradient(int number, int length, SetIntervalContext ic) {
        this.x = ic.numsToInterval(number, number);
        SetInterval[] dx = new SetInterval[length];
        Arrays.fill(dx, ic.numsToInterval(0, 0));
        this.dx = dx;
    }

    public Gradient(double number, int length, SetIntervalContext ic) {
        this.x = ic.numsToInterval(number, number);
        SetInterval[] dx = new SetInterval[length];
        Arrays.fill(dx, ic.numsToInterval(0, 0));
        this.dx = dx;
    }

    public Gradient(ExtendedRational number, int length, SetIntervalContext ic) {
        this.x = ic.numsToInterval(number, number);
        SetInterval[] dx = new SetInterval[length];
        Arrays.fill(dx, ic.numsToInterval(0, 0));
        this.dx = dx;
    }
}
