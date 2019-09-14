package me.nickcruz.jsonpokemon;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class PokemonActivity extends AppCompatActivity implements PokemonAdapterListener {

    private final PokemonAdapter pokemonAdapter = new PokemonAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpViewModel();
        setUpRecyclerView();
    }

    private void setUpRecyclerView() {
        RecyclerView pokemonRecyclerView = findViewById(R.id.pokemon_recycler_view);
        pokemonRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pokemonRecyclerView.setAdapter(pokemonAdapter);
        pokemonAdapter.setListener(this);
    }

    private void setUpViewModel() {
        PokemonViewModel pokemonViewModel = ViewModelProviders.of(this).get(PokemonViewModel.class);
        pokemonViewModel.getPokemon().observe(this, new Observer<List<PokemonModel>>() {
            @Override
            public void onChanged(List<PokemonModel> pokemon) {
                if (pokemon != null) {
                    pokemonAdapter.setPokemon(pokemon);
                }
            }
        });
        pokemonViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                showToast(errorMessage);
            }
        });
        pokemonViewModel.requestPokemon();
    }

    @Override
    public void onPokemonSelected(@NonNull PokemonModel pokemonModel) {
        showToast(String.format("Clicked on: %s", pokemonModel.getName()));
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
