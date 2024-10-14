import java.sql.*;
import java.util.ArrayList;

public class DB
{

    /**
     * Represents a connection to a database.
     *
     * The conn variable is used to establish a connection to a database and execute SQL queries.
     *
     * Methods in the DB class utilize the conn variable to execute queries such as SELECT, INSERT, UPDATE, and DELETE.
     *
     * This variable should be initialized with a valid Connection object before using any of the database-related methods.
     */
    private Connection conn;


    /**
     * Represents a connection to a database.
     */
    public DB(String address, String port, String databaseName, String username, String password)
    {
        //stringa di connessione -> jdbc:mysql://127.0.0.1:3306/nomeDB
        String dbConnectionString = "jdbc:mysql://" + address + ":" + port + "/" + databaseName;
        try
        {
            conn = DriverManager.getConnection(dbConnectionString, username, password);
            if (conn != null) System.out.println("Connessione avvenuta 👌");
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }


    /**
     * Executes a SELECT query on the database.
     *
     * @param what   the column(s) to select
     * @param from   the table(s) to select from
     * @param where  the condition to filter the result
     * @param is     the value to compare in the WHERE clause
     * @return a string representation of the selected data, formatted as tab-separated values
     * @throws SQLException if an SQL exception occurs while executing the query
     */
    public String select(String what, String from, String where, String is)
    {
        String result = "";
        try
        {
            if (!conn.isValid(5)) return null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return null;
        }
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
     * Executes a SELECT query on the database to retrieve all records from a specified table.
     *
     * @param from the name of the table to select from
     * @return a string representation of the selected data, formatted as tab-separated values
     * @throws SQLException if an SQL exception occurs while executing the query
     */
    public String selectALL(String from)
    {
        String result = "";
        try
        {
            if (!conn.isValid(5)) return null;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
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
     * Inserts a new record into the "Poke" table in the database.
     *
     * @param nome        the name of the person
     * @return true if the insert operation was successful, false otherwise
     */
    public boolean insertIntoPoke(String nome)
    {
        int id;
        String tipo1, tipo2;

        try
        {
            if (!conn.isValid(5)) return false;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

        try
        {
            Pokemon pokemon = PokeAPI.GET(nome);
            id = pokemon.getId();
            tipo1 = pokemon.getType().get(0);
            if (pokemon.getType().size() > 1) tipo2 = pokemon.getType().get(1);
            else tipo2 = null;
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }

        String query = "INSERT INTO poke (id, nome, tipo1, tipo2) VALUES (?, ?, ?, ?)";
        try
        {
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            statement.setString(2, nome);
            statement.setString(3, tipo1);
            statement.setString(4, tipo2);
            statement.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}