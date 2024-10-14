import com.mysql.cj.jdbc.exceptions.CommunicationsException;

import java.sql.SQLException;
import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        DB pokemons;
        try
        {
            pokemons = new DB("127.0.0.1", "3306", "pokemon", "root", "");
        }
        catch (SQLException e)
        {
            System.out.println("❌ Errore database: " + e.getMessage());
            return;
        }

        String resp;
        Scanner sc = new Scanner(System.in);
        while (true)
        {
            System.out.println("POKEDEX\nCosa desideri fare?\n1. Visualizzare tabella Pokémon\n2. Inserire nuovo Pokémon\n3. Uscire");
            resp = sc.nextLine();

            switch (resp)
            {
                case "1":
                    try
                    {
                        System.out.println(pokemons.selectALL("poke"));
                    }
                    catch (SQLException e)
                    {
                        System.out.println("❌ Errore database: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("INSERIMENTO DEL POKEMON\nNome Pokémon");
                    String nome = sc.nextLine();

                    try
                    {
                        pokemons.insertIntoPoke(nome);
                        System.out.println("✅ Pokémon inserito con successo!");
                    }
                    catch (NullPointerException e)
                    {
                        System.out.println("⚠️ Il Pokémon non esiste!");
                    }
                    catch (CommunicationsException e)
                    {
                        System.out.println("❌ Errore database: " + e.getMessage());
                    }
                    catch (SQLException e)
                    {
                        System.out.println("⚠️ Il Pokémon " + nome + " è già stato inserito!");
                    }
                    break;
                case "3":
                    return;
            }
        }
    }
}