package me.nickcruz.jsonpokemon.repository;

import android.text.format.DateUtils;
import android.util.Log;

import androidx.collection.ArrayMap;

import com.android.volley.Request;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import me.nickcruz.jsonpokemon.PokemonApplication;
import me.nickcruz.jsonpokemon.PokemonModel;
import me.nickcruz.jsonpokemon.api.GetPokemonEndpoint;
import me.nickcruz.jsonpokemon.db.PokemonDatabase;
import me.nickcruz.jsonpokemon.db.PokemonModelEntity;

public class PokemonRepository implements GetPokemonEndpoint.GetPokemonResponseHandler {

    private static final String TAG = PokemonRepository.class.getSimpleName();

    private static final Long REQUEST_AGE_STALE = 10 * DateUtils.MINUTE_IN_MILLIS;

    private final PokemonApplication application = PokemonApplication.getInstance();
    private final PokemonDatabase pokemonDatabase = application.getPokemonDatabase();
    private final GetPokemonEndpoint getPokemonEndpoint = new GetPokemonEndpoint();
    private final GetPokemonDatabaseWriter getPokemonDatabaseWriter = new GetPokemonDatabaseWriter(pokemonDatabase);

    private final List<GetPokemonEndpoint.GetPokemonResponseHandler> responseHandlers = new ArrayList<>();
    private final Map<Request<?>, Long> requestTimestamps = new ArrayMap<>();

    public void addResponseHandler(GetPokemonEndpoint.GetPokemonResponseHandler responseHandler) {
        responseHandlers.add(responseHandler);
    }

    public void requestPokemon() {
        Request<String> request = getPokemonEndpoint.getPokemon(this);
        Long requestAge = requestTimestamps.get(request);
        long currentTimeMillis = System.currentTimeMillis();

        // This will crash the app unless moved to a background thread
        List<PokemonModelEntity> pokemonEntities = pokemonDatabase.pokemonDao().getAll();

        if (pokemonEntities.isEmpty() || requestAge == null || currentTimeMillis - requestAge >= REQUEST_AGE_STALE) {
            if (pokemonEntities.isEmpty()) {
                Log.v(TAG, "No entities in db.");
            } else if (requestAge == null) {
                Log.v(TAG, "New request made: " + request.getUrl());
            } else {
                Log.v(TAG, String.format("Found matching stale request: %s, Timestamp: %s", request.getUrl(), requestAge));
            }
            requestPokemonFromNetwork(request, currentTimeMillis);
        } else {
            requestPokemonFromDatabase(pokemonEntities);
        }
    }

    private void requestPokemonFromNetwork(Request<String> request, long currentTimeMillis) {
        Log.v(TAG, String.format("Requesting pokemon from URL: %s", request.getUrl()));
        responseHandlers.add(getPokemonDatabaseWriter);
        Volley.newRequestQueue(application).add(request);
        requestTimestamps.put(request, currentTimeMillis);
    }

    private void requestPokemonFromDatabase(List<PokemonModelEntity> entities) {
        Log.v(TAG, "Requesting pokemon from db.");
        List<PokemonModel> pokemon = new ArrayList<>(entities.size());
        for (PokemonModelEntity entity : entities) {
            pokemon.add(new PokemonModel(entity.name, entity.url));
        }
        onGetPokemonResponse(pokemon);
    }

    @Override
    public void onGetPokemonResponse(List<PokemonModel> pokemon) {
        Log.v(TAG, String.format("onGetPokemonResponse: %s", pokemon.toString()));
        for (GetPokemonEndpoint.GetPokemonResponseHandler responseHandler : responseHandlers) {
            responseHandler.onGetPokemonResponse(pokemon);
        }
        responseHandlers.remove(getPokemonDatabaseWriter);
    }

    @Override
    public void onError(String error) {
        Log.w(TAG, String.format("onError: %s", error));
        for (GetPokemonEndpoint.GetPokemonResponseHandler responseHandler : responseHandlers) {
            responseHandler.onError(error);
        }
    }

    private static class GetPokemonDatabaseWriter implements GetPokemonEndpoint.GetPokemonResponseHandler {

        private PokemonDatabase pokemonDatabase;

        public GetPokemonDatabaseWriter(PokemonDatabase pokemonDatabase) {
            this.pokemonDatabase = pokemonDatabase;
        }

        @Override
        public void onGetPokemonResponse(List<PokemonModel> pokemon) {
            PokemonModelEntity[] entities = new PokemonModelEntity[pokemon.size()];
            for (int i = 0; i < pokemon.size(); ++i) {
                PokemonModel pokemonModel = pokemon.get(i);
                entities[i] = new PokemonModelEntity(pokemonModel.getName(), pokemonModel.getUrl());
            }
            pokemonDatabase.pokemonDao().insertAll(entities);
        }

        @Override
        public void onError(String error) {
            // Do nothing
        }
    }
}
