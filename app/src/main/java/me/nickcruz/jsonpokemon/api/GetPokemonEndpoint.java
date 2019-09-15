package me.nickcruz.jsonpokemon.api;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.nickcruz.jsonpokemon.PokemonModel;
import me.nickcruz.jsonpokemon.repository.ErrorHandler;

public class GetPokemonEndpoint {

    private static final String TAG = GetPokemonEndpoint.class.getSimpleName();

    private static final String PATH = "pokemon/";

    public Request<String> getPokemon(final GetPokemonResponseHandler getPokemonResponseHandler) {
        return new StringRequest(Request.Method.GET, getURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    getPokemonResponseHandler.onGetPokemonResponse(parseResponse(response));
                } catch (JSONException e) {
                    Log.e(TAG, e.getMessage(), e);
                    getPokemonResponseHandler.onError(e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                getPokemonResponseHandler.onError(error.getMessage());
            }
        });
    }

    public List<PokemonModel> parseResponse(String response) throws JSONException {
        List<PokemonModel> pokemon = new ArrayList<>();
        JSONObject jsonObject = new JSONObject(response);
        JSONArray results = jsonObject.getJSONArray("results");
        for (int i = 0; i < results.length(); ++i) {
            JSONObject result = (JSONObject) results.get(i);
            String name = result.getString("name");
            String url = result.getString("url");
            pokemon.add(new PokemonModel(name, url));
        }
        return pokemon;
    }

    private String getURL() {
        return PokemonAPI.API_BASE + PATH;
    }
    public interface GetPokemonResponseHandler extends ErrorHandler {


        void onGetPokemonResponse(List<PokemonModel> pokemon);
    }
}
