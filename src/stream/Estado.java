package stream;

/**
 * Created by Bext on 15/02/2017.
 */
public class Estado {
    private String Name;

    public Estado(String name) {
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
        return "Estado{" +
                "Name='" + Name + '\'' +
                '}';
    }
}
