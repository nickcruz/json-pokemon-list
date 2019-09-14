package me.nickcruz.jsonpokemon;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

public class PokemonViewModel extends AndroidViewModel {

    private static final String API_BASE = "https://pokeapi.co/api/v2/";
    private static final String GET_POKEMON = "pokemon/";

    private final MutableLiveData<List<PokemonModel>> pokemon = new MutableLiveData<>((List<PokemonModel>) new ArrayList<PokemonModel>());
    private final MutableLiveData<String> error = new MutableLiveData<>();
    
    private final GetPokemonResponseHandler getPokemonResponseHandler = new GetPokemonResponseHandler();
    private final ErrorHandler errorHandler = new ErrorHandler();

    public PokemonViewModel(@NonNull Application application) {
        super(application);
    }

    void requestPokemon() {
        List<PokemonModel> value = pokemon.getValue();
        if (value == null || value.isEmpty()) {
            Volley.newRequestQueue(getApplication())
                    .add(new StringRequest(Request.Method.GET, API_BASE + GET_POKEMON, getPokemonResponseHandler, errorHandler));
        }
    }

    LiveData<List<PokemonModel>> getPokemon() {
        return pokemon;
    }

    LiveData<String> getError() {
        return error;
    }

    private class GetPokemonResponseHandler implements Response.Listener<String> {
        @Override
        public void onResponse(String response) {
            error.postValue(null);
            Log.v(PokemonViewModel.class.getSimpleName(), response);
            // TODO(nick) parse json response
        }
    }

    private class ErrorHandler implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
            PokemonViewModel.this.error.postValue(error.getMessage());
        }
    }
}
