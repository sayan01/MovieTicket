import com.mashape.unirest.http.*; 
import com.mashape.unirest.request.GetRequest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.*;
import java.io.*;
import java.util.*;
/*
 *   This class fetches the online movie database from the tMDb open database API
 *   It uses its unique API key to query the database for movies which are showing in theatres now
 */
class GetMoviesOnline {
	/*
	 *   Method: getMovies()
	 *   Parameters: none
	 *   Returns: String[]
	 *   Desc: 
	 *   This method returns the first 15-20 results of the query of movies now showing
	 *   It uses the Unirest class to send the API request to tMDb and get the response
	 *   It uses org.json class to parse the JSON response
	 *   It stores the title of the movies in an array then returns it
	 */
	static String[] getMovies() throws UnirestException, FileNotFoundException {
		String api_key = getKey();
		GetRequest gr = Unirest.get("https://api.themoviedb.org/3/movie/now_playing?" + //Querying Now Showing Movies
				"api_key=" + api_key +    //API key needed to access database
				"&language=en-US" +                             //Language of the movie to be queried
				"&page=1");                                     //Page whose results to be used
		JSONObject obj = new JSONObject(gr.asString().getBody());   // Storing returned request as an JSON object
		JSONArray arr = obj.getJSONArray("results");            // Storing the results objects array of the response
		String[] movies = new String[arr.length()];
		for (int i = 0; i < arr.length(); i++) {                    // Iterating over all the results
			String name = arr.getJSONObject(i).getString("title");  // Storing the name of each movie
			movies[i] = name;
		}
		return movies;                                              // Returning the array of movie titles
	}
	private static String getKey() throws FileNotFoundException{
		File f = new File("APIKEY");
		return new Scanner(f).nextLine();
	}
}
