import com.google.gson.*;
import java.io.*;
import java.net.*;

public class GIFAPI
{
    /**
     * Classe per il collegamento all'API di Giphy
     */
    private static String API_KEY = "uhRuwT82T4ImLobNaWU7jmVOtQAdJpww"; //token per l'uso dell'API
    private static String GIPHY_SEARCH_URL = "https://api.giphy.com/v1/gifs/search"; //URL per l'uso dell'API

    /**
     * Ottiene la GIF dall'API di Giphy (GET)
     * @param nome  stringa da cercare
     * @return l'URL della GIF di Giphy
     * @throws Exception se viene lanciata un'eccezione
     */
    public static String GET(String nome) throws Exception
    {
        try
        {
            String urlString = GIPHY_SEARCH_URL + "?api_key=" + API_KEY + "&q=" + nome + "&limit=1"; //l'ultima parte serve per aver solo un risultato

            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();

            while ((inputLine = in.readLine()) != null)
            {
                content.append(inputLine);
            }

            in.close();
            connection.disconnect();

            JsonObject jsonResponse = JsonParser.parseString(content.toString()).getAsJsonObject();
            String gifUrl = jsonResponse
                    .getAsJsonArray("data")
                    .get(0)
                    .getAsJsonObject()
                    .getAsJsonObject("images")
                    .getAsJsonObject("original")
                    .get("url")
                    .getAsString();

            return gifUrl;

        }
        catch (Exception e)
        {
            throw new Exception();
        }
    }
}
