import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GIFAPI
{
    private static String API_KEY = "uhRuwT82T4ImLobNaWU7jmVOtQAdJpww";
    private static String GIPHY_SEARCH_URL = "https://api.giphy.com/v1/gifs/search";

    public static String GET(String nome) throws Exception {
        try
        {
            String urlString = GIPHY_SEARCH_URL + "?api_key=" + API_KEY + "&q=" + nome + "&limit=1";

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

        } catch (Exception e)
        {
            throw new Exception();
        }
    }
}
