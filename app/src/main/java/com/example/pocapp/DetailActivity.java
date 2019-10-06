package com.example.pocapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.pocapp.model.Pokemon;

public class DetailActivity extends AppCompatActivity {

    private ImageView imageViewPok, imageLike;
    private TextView textViewName;
    private Pokemon pokemon;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageViewPok = findViewById(R.id.imagePokemon);
        textViewName = findViewById(R.id.textName);
        imageLike = findViewById(R.id.imageViewLikeDetail);

        pokemon = getIntent().getParcelableExtra("pokemon");
        if (pokemon != null) {
            String name = pokemon.getName();
            textViewName.setText(name);
        }

        Glide.with(this)
                .load("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + pokemon.getNumber() + ".png")
                .into(imageViewPok);

         imageLike.setOnClickListener(new View.OnClickListener() {
             private boolean fun = true;

             @Override
             public void onClick(View v) {
                 preferences = getApplicationContext().getSharedPreferences("Like", MODE_PRIVATE);
                 SharedPreferences.Editor editor = preferences.edit();
                 if (fun){
                     imageLike.setImageResource(R.drawable.ic_like);
                     editor.putBoolean("LikeOrNot", true).apply();
                     fun = false;
                 } else {
                     fun = true;
                     imageLike.setImageResource(R.drawable.ic_unlike);
                     editor.putBoolean("LikeOrNot", false).apply();
                 }
             }
         });
    }
}
