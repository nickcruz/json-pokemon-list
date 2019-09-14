package me.nickcruz.jsonpokemon;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.PokemonViewHolder> implements PokemonAdapterListener {

    private final List<PokemonModel> pokemon = new ArrayList<>();

    @Nullable
    private PokemonAdapterListener listener;

    public void setPokemon(List<PokemonModel> pokemon) {
        this.pokemon.clear();
        this.pokemon.addAll(pokemon);
        notifyDataSetChanged();
    }

    public void setListener(@Nullable PokemonAdapterListener listener) {
        this.listener = listener;
    }

    @Override
    public void onPokemonSelected(@NonNull PokemonModel pokemonModel) {
        if (listener != null) {
            listener.onPokemonSelected(pokemonModel);
        }
    }

    @NonNull
    @Override
    public PokemonAdapter.PokemonViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pokemon, parent, false);
        return new PokemonViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull PokemonAdapter.PokemonViewHolder holder, int position) {
        holder.setPokemonModel(pokemon.get(position));
    }

    @Override
    public int getItemCount() {
        return pokemon.size();
    }

    static class PokemonViewHolder extends RecyclerView.ViewHolder {

        private TextView nameText;

        @Nullable
        private PokemonModel pokemonModel;

        PokemonViewHolder(@NonNull View itemView, final PokemonAdapterListener listener) {
            super(itemView);
            nameText = itemView.findViewById(R.id.pokemon_name_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (pokemonModel != null) {
                        listener.onPokemonSelected(pokemonModel);
                    }
                }
            });
        }

        void setPokemonModel(PokemonModel pokemonModel) {
            this.pokemonModel = pokemonModel;
            nameText.setText(pokemonModel.getName());
        }

    }

}
