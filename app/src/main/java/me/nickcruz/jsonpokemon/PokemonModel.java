package me.nickcruz.jsonpokemon;

public class PokemonModel {

    private final String name;
    private final String url;

    public PokemonModel(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }
}
