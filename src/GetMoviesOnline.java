
import com.mashape.unirest.http.*;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import org.json.*;

public class GetMoviesOnline {

    static String[] getMovies() throws UnirestException {

        GetRequest gr = Unirest.get("https://api.themoviedb.org/3/movie/now_playing?" + //Querying Now Showing Movies
                "api_key=184059728615a4a82769a144d29a69d8" +    //API key needed to access database
                "&language=en-US" +                             //Language of the movie to be queried
                "&page=1");                                     //Page whose results to be used


        JSONObject obj = new JSONObject(gr.asString().getBody());   // Storing returned request as an JSON object
        JSONArray arr = obj.getJSONArray("results");            // Storing the results objects array of the response
        String[] movies = new String[arr.length()];
        for (int i = 0; i < arr.length(); i++) {
            String name = arr.getJSONObject(i).getString("title");  // Storing the name of the movie
            movies[i] = name;
        }

        return movies;
    }
}