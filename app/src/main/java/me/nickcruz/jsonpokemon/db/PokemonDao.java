package me.nickcruz.jsonpokemon.db;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PokemonDao {

    @Query("SELECT * FROM pokemonmodelentity")
    List<PokemonModelEntity> getAll();

    @Insert
    void insertAll(PokemonModelEntity... pokemonModelEntities);
}
