package me.nickcruz.jsonpokemon.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {PokemonModelEntity.class}, version = 1)
public abstract class PokemonDatabase extends RoomDatabase {

    public abstract PokemonDao pokemonDao();
}
