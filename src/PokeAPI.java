import com.google.gson.Gson;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class PokeAPI
{
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";
    private static HttpClient client = HttpClient.newHttpClient();
    private static Gson gson = new Gson();

    public static Pokemon GET(String nome)
    {
        try
        {
            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URL + nome))
                    .header("Content-Type", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200)
            {
                String jsonResponse = response.body();
                Gson gson = new Gson();
                return gson.fromJson(jsonResponse, Pokemon.class);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
