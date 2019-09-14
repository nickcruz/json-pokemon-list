package me.nickcruz.jsonpokemon;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class PokemonViewModel extends ViewModel {

    private MutableLiveData<List<PokemonModel>> pokemon = new MutableLiveData<>((List<PokemonModel>) new ArrayList<PokemonModel>());

    public void requestPokemon() {
        List<PokemonModel> value = pokemon.getValue();
        if (value == null || value.isEmpty()) {
            // TODO(nick) GET from API
        }
    }

    public LiveData<List<PokemonModel>> getPokemon() {
        return pokemon;
    }
}
