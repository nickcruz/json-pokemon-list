package me.nickcruz.jsonpokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

public class PokemonActivity extends AppCompatActivity implements PokemonAdapterListener {

    private final PokemonAdapter pokemonAdapter = new PokemonAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView pokemonRecyclerView = findViewById(R.id.pokemon_recycler_view);
        pokemonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pokemonRecyclerView.setAdapter(pokemonAdapter);

        pokemonAdapter.setListener(this);
    }

    @Override
    public void onPokemonSelected(@NonNull PokemonModel pokemonModel) {
        Toast.makeText(this, String.format("Clicked on: %s", pokemonModel.getName()), Toast.LENGTH_SHORT).show();
    }
}
