package optimization;

import net.java.jinterval.interval.set.SetInterval;
import net.java.jinterval.rational.ExtendedRational;

public class ListItem implements Comparable<ListItem> {
    private SetInterval[] argument;
    private SetInterval estimation;
    private ExtendedRational wid;

    public ListItem(SetInterval[] argument, SetInterval estimation, ExtendedRational wid) {
        setArgument(argument);
        setEstimation(estimation);
        setWid(wid);
    }

    public SetInterval[] getArgument() {
        return argument;
    }

    private void setArgument(SetInterval[] argument) {
        this.argument = argument;
    }

    public SetInterval getEstimation() {
        return estimation;
    }

    private void setEstimation(SetInterval estimation) {
        this.estimation = estimation;
    }

    public ExtendedRational getWid() {
        return wid;
    }

    private void setWid(ExtendedRational wid) {
        this.wid = wid;
    }

    public int compareTo(ListItem other) {
        return ExtendedRational.valueOf(other.estimation.sup())
            .compareTo(ExtendedRational.valueOf(this.estimation.sup()));
    }

    @Override
    public String toString() {
        StringBuilder outString = new StringBuilder();

        for (int i = 0; i < this.getArgument().length; i++) {
            outString
                .append("([")
                .append(this.getArgument()[i].doubleInf())
                .append(", ")
                .append(this.getArgument()[i].doubleSup());

            if (i != this.getArgument().length - 1) {
                outString.append("], ");
            } else {
                outString.append("]");
            }
        }
        outString
            .append(") | f: [")
            .append(this.getEstimation().doubleInf())
            .append(", ").append(this.getEstimation().doubleSup())
            .append("]\n");

        return outString.toString();
    }
}
