package br.com.projeto.api;

import com.google.gson.Gson;

import br.com.projeto.api.modelo.DTOFilme;
import io.github.cdimascio.dotenv.Dotenv;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TMDBClient {
    Dotenv dotenv = Dotenv.load();
    private final String API_KEY;
    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    private OkHttpClient client;
    private Gson gson;

    public TMDBClient() {
        this.client = new OkHttpClient();
        this.gson = new Gson();
        this.API_KEY = dotenv.get("TMDB_API_KEY"); // Replace with your TMDB API key
    }
    
    public DTOFilme getMovieDetails(int movieId) {
        String url = BASE_URL + movieId;

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("Accept", "application/json")
                .addHeader("Authorization", "Bearer " + API_KEY)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (response.isSuccessful()) {
                String responseData = response.body().string();
                // Parse the JSON response to a Movie object
                return gson.fromJson(responseData, DTOFilme.class);
            } else {
                System.out.println("Request failed: " + response.code());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if parsing failed or request was unsuccessful
    }
}