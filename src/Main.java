public class Main
{
    public static void main(String[] args)
    {
        DB pokemons = new DB("127.0.0.1", "3306", "pokemon", "root", "");
        pokemons.insertIntoPoke("pikachu");
        System.out.println(pokemons.selectALL("poke"));
    }
}