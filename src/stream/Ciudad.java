package stream;

/**
 * Created by Bext on 15/02/2017.
 */
public class Ciudad {
    private String Name;

    public Ciudad(String name) {
        Name = name;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Ciudad{" +
                "Name='" + Name + '\'' +
                '}';
    }
}
