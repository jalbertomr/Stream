package stream;

import java.util.List;

/**
 * Created by Bext on 15/02/2017.
 */
public class Item {
    private Estado key1;
    private List<SubItem> subItems;

    public Item(Estado key1, List<SubItem> subItems) {
        this.key1 = key1;
        this.subItems = subItems;
    }

    public Estado getKey1() {
        return key1;
    }

    public void setKey1(Estado key1) {
        this.key1 = key1;
    }

    public List<SubItem> getSubItems() {
        return subItems;
    }

    public void setSubItems(List<SubItem> subItems) {
        this.subItems = subItems;
    }
}
