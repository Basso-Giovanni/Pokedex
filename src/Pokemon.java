import java.util.ArrayList;

public class Pokemon {
    int id;
    String name;
    ArrayList<String> types;

    public Pokemon()
    {
        this.id = 0;
        this.name = "";
        this.types = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getType() {
        return types;
    }

    public void AggiungiTipo(String tipo)
    {
        this.types.add(tipo);
    }
}