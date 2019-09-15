package me.nickcruz.jsonpokemon.api;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import me.nickcruz.jsonpokemon.PokemonModel;

public class GetPokemonEndpoint {

    private static final String PATH = "pokemon/";

    public String getURL() {
        return PokemonAPI.API_BASE + PATH;
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
}
