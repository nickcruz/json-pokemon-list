package me.nickcruz.jsonpokemon.repository;

import android.app.Application;

import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import me.nickcruz.jsonpokemon.PokemonApplication;
import me.nickcruz.jsonpokemon.PokemonModel;
import me.nickcruz.jsonpokemon.api.GetPokemonEndpoint;

public class PokemonRepository implements GetPokemonEndpoint.GetPokemonResponseHandler {

    private final Application application = PokemonApplication.getInstance();
    private final GetPokemonEndpoint getPokemonEndpoint = new GetPokemonEndpoint();
    private final List<GetPokemonEndpoint.GetPokemonResponseHandler> responseHandlers = new ArrayList<>();

    public void addResponseHandler(GetPokemonEndpoint.GetPokemonResponseHandler responseHandler) {
        responseHandlers.add(responseHandler);
    }

    public void requestPokemon() {
        // TODO(nick) If this was a recently-made call, skip the endpoint and call Room
        // Otherwise, make the request, add a DB write response handler, then remove it
        Volley.newRequestQueue(application)
                .add(getPokemonEndpoint.getPokemon(this));
    }

    @Override
    public void onGetPokemonResponse(List<PokemonModel> pokemon) {
        for (GetPokemonEndpoint.GetPokemonResponseHandler responseHandler : responseHandlers) {
            responseHandler.onGetPokemonResponse(pokemon);
        }
    }

    @Override
    public void onError(String error) {
        for (GetPokemonEndpoint.GetPokemonResponseHandler responseHandler : responseHandlers) {
            responseHandler.onError(error);
        }
    }
}
