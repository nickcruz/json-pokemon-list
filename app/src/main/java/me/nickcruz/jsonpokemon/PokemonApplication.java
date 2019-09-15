package me.nickcruz.jsonpokemon;

import android.app.Application;

import androidx.room.Room;

import me.nickcruz.jsonpokemon.db.PokemonDatabase;

public class PokemonApplication extends Application {

    private static PokemonApplication INSTANCE;

    public static PokemonApplication getInstance() {
        return INSTANCE;
    }

    private PokemonDatabase pokemonDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
        pokemonDatabase = Room.databaseBuilder(this, PokemonDatabase.class, "pokemon-db").build();
    }

    public PokemonDatabase getPokemonDatabase() {
        return pokemonDatabase;
    }
}
