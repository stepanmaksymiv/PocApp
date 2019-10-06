package com.example.pocapp.retrofit;

import com.example.pocapp.model.PokemonResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiService {

    @GET("pokemon")
    Call<PokemonResponse> getAllPokemon(@Query("limit") int limit, @Query("offset") int offset);
}
