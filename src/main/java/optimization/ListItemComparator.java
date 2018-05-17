package optimization;

import java.util.Comparator;

public class ListItemComparator implements Comparator<ListItem> {
    public int compare(ListItem firstElement, ListItem secondElement) {
        return firstElement.compareTo(secondElement);
    }
}
