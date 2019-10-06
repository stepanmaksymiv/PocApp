package com.example.pocapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pocapp.model.Pokemon;
import com.example.pocapp.model.PokemonResponse;
import com.example.pocapp.retrofit.ApiService;
import com.example.pocapp.retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity {

    private PokemonAdapter adapter;
    private RecyclerView recyclerView;
    private List<Pokemon>pokemonList;
    private int offset;
    private boolean isNext;
    private ProgressBar progressBar;
    private SwipeRefreshLayout refreshLayout;
    private TextView textErrorList;
    private SharedPreferences preferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        progressBar = findViewById(R.id.progressBar);
        recyclerView = findViewById(R.id.recycler_view);
        refreshLayout = findViewById(R.id.swipeLayout);
        textErrorList = findViewById(R.id.textErrorList);

        refreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData(offset);
            }
        });

        recyclerView.setVisibility(View.INVISIBLE);

        adapter = new PokemonAdapter(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration divider = new DividerItemDecoration(recyclerView.getContext(), layoutManager.getOrientation());
        recyclerView.addItemDecoration(divider);

        adapter.setListener(new PokemonAdapter.Listener() {
            @Override
            public void onItemClicked(int position) {
                Pokemon pokemon = pokemonList.get(position);
                Toast.makeText(ListActivity.this, "Position is: " + pokemon.getName(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ListActivity.this, DetailActivity.class);
                intent.putExtra("pokemon", pokemon);
                startActivity(intent);
            }
        });
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (dy > 0) {
                    int visibleItemCount = layoutManager.getChildCount();
                    int totalItemCount = layoutManager.getItemCount();
                    int pastVisibleItems = layoutManager.findFirstVisibleItemPosition();

                    if (isNext) {
                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                            Log.i("ListActivity", "Final");

                            isNext = false;
                            offset += 20;
                            getData(offset);
                        }
                    }
                }
            }
        });

        isNext = true;
        offset = 0;
        getData(offset);

    }


        private void getData(int offset) {
        if (isNetworkAvailable()){
            refreshLayout.setRefreshing(true);
            ApiService service = RetrofitClient.getClient().create(ApiService.class);
            Call<PokemonResponse>call = service.getAllPokemon(20, offset);
            call.enqueue(new Callback<PokemonResponse>() {
                @Override
                public void onResponse(Call<PokemonResponse> call, Response<PokemonResponse> response) {
                    isNext = true;
                    refreshLayout.setRefreshing(false);
                    PokemonResponse pokemonResponse = response.body();
                    if (pokemonResponse != null){
                        recyclerView.setVisibility(View.VISIBLE);
                        pokemonList = pokemonResponse.getPokemonList();
                        adapter.addAllPokemon(pokemonList);
                        progressBar.setVisibility(View.GONE);
                    }
                }

                @Override
                public void onFailure(Call<PokemonResponse> call, Throwable t) {
                    isNext = true;
                    refreshLayout.setRefreshing(false);
                    Log.e("ListActivity", " onFailure: " + t.getMessage());
                }
            });
        } else {
            Toast.makeText(this, "Sorry, network is not available,\n please try later", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
            textErrorList.setVisibility(View.VISIBLE);
            textErrorList.setText(getResources().getString(R.string.string_error));
        }
    }

    private boolean isNetworkAvailable(){
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        boolean isConnected = false;
        if (manager != null){
            NetworkInfo networkInfo = manager.getActiveNetworkInfo();
            isConnected = (networkInfo != null) && (networkInfo.isConnectedOrConnecting());
        }
        return isConnected;
    }




}
