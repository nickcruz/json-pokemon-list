package me.nickcruz.jsonpokemon;

import androidx.annotation.NonNull;

public interface PokemonAdapterListener {
    void onPokemonSelected(@NonNull PokemonModel pokemonModel);
}
