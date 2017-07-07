package stream;

import java.util.List;

/**
 * Created by Bext on 15/02/2017.
 */
public class SubItem {
    private Ciudad key2;
    private List<String> referencias;

    public SubItem(Ciudad key2, List<String> referencias) {
        this.key2 = key2;
        this.referencias = referencias;
    }

    public SubItem getThis() {
      return this;
    }

    public SubItem(Ciudad key2) {
        this.key2 = key2;
    }

    public Ciudad getKey2() {
        return key2;
    }

    public void setKey2(Ciudad key2) {
        this.key2 = key2;
    }

    public List<String> getReferencias() {
        return referencias;
    }

    public void setReferencias(List<String> referencias) {
        this.referencias = referencias;
    }
}
