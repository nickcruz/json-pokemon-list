package me.nickcruz.jsonpokemon.db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Room Entity version of {@link me.nickcruz.jsonpokemon.PokemonModel}.
 */
@Entity
public class PokemonModelEntity {

    // 0 is treated as unset in room
    // https://stackoverflow.com/a/50079226
    private static final int UID_UNSET = 0;

    public PokemonModelEntity(String name, String url) {
        this.uid = UID_UNSET;
        this.name = name;
        this.url = url;
    }

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "url")
    public String url;
}
