package br.com.guilhermecunha.pokedex.api

import br.com.guilhermecunha.pokedex.model.HealthResponse
import retrofit2.Call
import retrofit2.http.GET

interface PokemonService {
    @GET("/api/pokemon/health")
    fun checkHealth(): Call<HealthResponse>
}