import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import javax.swing.*;
import java.net.URL;
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
                        if (pokemons.insertIntoPoke(nome))
                        {
                            System.out.println("✅ Pokémon inserito con successo!");
                            Visualizza(nome, false);
                        }
                        else System.out.println("⚠️ Il Pokémon non è stato inserito!");
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
                    Visualizza("bye", true);
                    return;
            }
        }
    }

    /**
     * Metodo per visualizzare un form con la GIF cercata dall'API di Giphy
     * @param nome      termine da cercare per l'API di Giphy
     * @param chiusura  true se alla chiusura del form il codice deve arrestarsi, altrimenti false
     */
    static void Visualizza(String nome, boolean chiusura)
    {
        try
        {
            String gifUrl = GIFAPI.GET(nome);
            if (gifUrl == null) return;

            URL url = new URL(gifUrl);

            JFrame frame = new JFrame(nome);
            Icon icon = new ImageIcon(url);
            JLabel label = new JLabel(icon);

            frame.add(label);
            if (chiusura) frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setAlwaysOnTop(true);
            frame.setVisible(true);

        }
        catch (Exception e)
        {
            System.out.println("⚠️ GIF non trovata");
        }
    }
}