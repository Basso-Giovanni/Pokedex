import java.util.ArrayList;

public class Pokemon
{
    /**
     * Classe rappresentante le informazioni dei Pokémon
     */
    int id; //id pokedex
    String name; //nome del Pokémon
    ArrayList<String> types; //lista con i due tipi del Pokémon

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

    @Override
    public String toString()
    {
        String out = id + "\t" + name + "\t";
        for (String tipo : types) out += tipo + "\t";
        return out;
    }
}