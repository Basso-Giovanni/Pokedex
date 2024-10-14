import com.google.gson.*;
import java.net.URI;
import java.net.http.*;

public class PokeAPI
{
    private static final String BASE_URL = "https://pokeapi.co/api/v2/pokemon/";

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
                Pokemon pokemon = new Pokemon();
                String jsonResponse = response.body();

                JsonObject jsonObject = JsonParser.parseString(jsonResponse).getAsJsonObject();
                pokemon.setId(jsonObject.get("id").getAsInt());
                pokemon.setName(jsonObject.get("name").getAsString());
                JsonArray typesArray = jsonObject.getAsJsonArray("types");
                for (JsonElement type : typesArray)
                    pokemon.AggiungiTipo(type.getAsJsonObject().getAsJsonObject("type").get("name").getAsString());
                return pokemon;
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
