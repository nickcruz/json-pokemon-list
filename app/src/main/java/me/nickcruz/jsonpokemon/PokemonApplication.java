package me.nickcruz.jsonpokemon;

import android.app.Application;

public class PokemonApplication extends Application {

    private static PokemonApplication INSTANCE;

    public static PokemonApplication getInstance() {
        return INSTANCE;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        INSTANCE = this;
    }
}
