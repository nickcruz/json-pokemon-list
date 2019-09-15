package me.nickcruz.jsonpokemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import me.nickcruz.jsonpokemon.api.GetPokemonEndpoint;
import me.nickcruz.jsonpokemon.repository.PokemonRepository;

public class PokemonViewModel extends ViewModel implements GetPokemonEndpoint.GetPokemonResponseHandler {

    private final MutableLiveData<List<PokemonModel>> pokemon = new MutableLiveData<>((List<PokemonModel>) new ArrayList<PokemonModel>());
    private final MutableLiveData<String> error = new MutableLiveData<>();

    private final PokemonRepository pokemonRepository = new PokemonRepository();

    public PokemonViewModel() {
        pokemonRepository.addResponseHandler(this);
    }

    void requestPokemon() {
        List<PokemonModel> value = pokemon.getValue();
        if (value == null || value.isEmpty()) {
            pokemonRepository.requestPokemon();
        }
    }

    LiveData<List<PokemonModel>> getPokemon() {
        return pokemon;
    }

    LiveData<String> getError() {
        return error;
    }

    @Override
    public void onGetPokemonResponse(List<PokemonModel> pokemon) {
        this.pokemon.postValue(pokemon);
        error.postValue(null);
    }

    @Override
    public void onError(String error) {
        this.error.postValue(error);
    }
}
