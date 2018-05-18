package optimization;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.rational.ExtendedRational;

public class ListItemGradient extends ListItem {
    private SetInterval[] partials;

    public ListItemGradient(
        SetInterval[] argument,
        SetInterval estimation,
        ExtendedRational wid,
        SetInterval[] partials
    ) {
        super(argument, estimation, wid);
        this.partials = partials;
    }

    public SetInterval[] getPartials() {
        return partials;
    }
}
