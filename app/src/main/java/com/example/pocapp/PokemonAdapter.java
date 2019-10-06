package com.example.pocapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.pocapp.model.Pokemon;

import java.util.ArrayList;
import java.util.List;


public class PokemonAdapter extends RecyclerView.Adapter<PokemonAdapter.MyViewHolder> {

    private Context context;
    private List<Pokemon> pokemonList;
    private Listener listener;

    interface Listener{
        void onItemClicked(int position);
    }

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public PokemonAdapter(Context context) {
        this.context = context;
        pokemonList = new ArrayList<>();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pokemon_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        Pokemon pokemon = pokemonList.get(position);
        holder.textViewName.setText(pokemon.getName());
    }

    @Override
    public int getItemCount() {
        return pokemonList.size();
    }

    public void addAllPokemon(List<Pokemon>pokemons){
        pokemonList.addAll(pokemons);
        notifyDataSetChanged();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        private TextView textViewName;
        private ImageView imageViewLike;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewName = itemView.findViewById(R.id.textViewName);
            imageViewLike = itemView.findViewById(R.id.imageViewLike);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        listener.onItemClicked(getAdapterPosition());
                    }
                }
            });
        }
    }
}
