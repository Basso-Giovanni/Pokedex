import java.util.ArrayList;

public class Pokemon {
    int id;
    String name;
    ArrayList<Tipo> types;

    public Pokemon(int id, String name) {
        this.id = id;
        this.name = name;
        this.types = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Tipo> getType() {
        return types;
    }
}