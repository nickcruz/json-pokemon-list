package me.nickcruz.jsonpokemon.api;

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
        // TODO(nick) parse using JSONObject
        return pokemon;
    }
}
