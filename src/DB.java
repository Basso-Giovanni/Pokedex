import java.sql.*;
import java.util.Locale;
import java.util.Scanner;

public class DB
{
    /**
     * Rappresenta una connessione ad un database.
     *
     * La variabile conn permette di stabilire la connessione con il database.
     *
     * I metodi di questa classe permettono di eseguire SELECT e INSERT in una tabella.
     *
     * La variabile conn dovrebbe essere inizializzata prima di eseguire qualsiasi query SQL.
     */
    private Connection conn;


    /**
     * Costruttore per stabilire una connessione con il database.
     *
     * @param address       indica l'indirizzo IP del database
     * @param port          indica la porta TCP/UDP del database
     * @param databaseName  indica il nome del database
     * @param username      indica il nome utente per accedere al database
     * @param password      indica la password per accedere al database
     * @throws SQLException se la connessione non può essere instaurata
     */
    public DB(String address, String port, String databaseName, String username, String password) throws SQLException
    {
        //stringa di connessione -> jdbc:mysql://127.0.0.1:3306/nomeDB
        String dbConnectionString = "jdbc:mysql://" + address + ":" + port + "/" + databaseName;
        conn = DriverManager.getConnection(dbConnectionString, username, password);
        if (conn != null) System.out.println("Connessione avvenuta 👌");
    }


    /**
     * Esegue una query SELECT.
     *
     * @param what   quali colonne prendere dalla tabella
     * @param from   quale tabella prendere
     * @param where  eventuali condizioni
     * @param is     il valore da paragonare
     * @return una stringa con il risultato della query formattata in tabella
     * @throws SQLException se un'eccezione SQL viene lanciata durante la query
     */
    public String select(String what, String from, String where, String is) throws SQLException
    {
        String result = "";
        if (!conn.isValid(5)) return null;
        String query = "SELECT " + what + " FROM " + from + " WHERE " + where + " = ?";

        try
        {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, is);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    result += rs.getString(i) + "\t";
                    if (rs.getString(i).length() < 8) result += "\t";
                }
                result += "\n";
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        return result;
    }

    /**
     * Esegue una query SELECT stampando tutta la tabella di un database.
     *
     * @param from  nome della tabella
     * @return una stringa rappresentante il risultato della query, formattata in tabella
     * @throws SQLException se un'eccezione SQL viene lanciata durante la query
     */
    public String selectALL(String from) throws SQLException
    {
        String result = "";
        if (!conn.isValid(5)) return null;
        String query = "SELECT * FROM " + from;

        try
        {
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next())
            {
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
                {
                    result += rs.getString(i) + "\t";
                    //if the record is too short this if add a new tabulation
                    if (rs.getString(i) != null && rs.getString(i).length() < 8) result += "\t";
                }
                result += "\n";
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    /**
     * Inserisce un nuovo record nella tabella Poke del database.
     *
     * @param nome        nome del Pokémon
     * @return true se l'inserimento va a buon fine, altrimenti false
     * @throws SQLException se un'eccezione SQL viene lanciata durante la query
     */
    public boolean insertIntoPoke(String nome) throws SQLException
    {
        int id;
        String tipo1, tipo2;

        if (!conn.isValid(5)) return false;

        try
        {
            Pokemon pokemon = PokeAPI.GET(nome.toLowerCase(Locale.ROOT));
            id = pokemon.getId();
            tipo1 = pokemon.getType().get(0);
            if (pokemon.getType().size() > 1) tipo2 = pokemon.getType().get(1);
            else tipo2 = null;
            System.out.println(pokemon.toString());
        }
        catch (NullPointerException e)
        {
            throw new NullPointerException();
        }

        System.out.println("Vuoi aggiungere " + nome + "?\nS per confermare o qualsiasi tasto per annullare");
        Scanner sc = new Scanner(System.in);
        String resp = sc.nextLine();
        if (!resp.toLowerCase(Locale.ROOT).equals("s")) return false;

        String query = "INSERT INTO poke (id, nome, tipo1, tipo2) VALUES (?, ?, ?, ?)";
        try
        {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, nome.toUpperCase(Locale.ROOT));
            statement.setString(3, tipo1);
            statement.setString(4, tipo2);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            throw new SQLException();
        }
        return true;
    }
}