package com.KnowledgeTest.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.KnowledgeTest.Model.Pokemon;
import com.KnowledgeTest.Model.PokemonURL;

@Service
public class PokemonService {

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Returns a list of All PokemonURL in the Pokemon API
	 * 
	 * @return PokemonUrlList
	 */
	public List<PokemonURL> getAllPokemonURL() {
		// Make a GET request to the Pokemon API endpoint to get the total number of
		// available Pokemon
		String url = "https://pokeapi.co/api/v2/pokemon";
		String responsePokemonCount = restTemplate.getForObject(url, String.class);
		JSONObject jsonPokemonCount = new JSONObject(responsePokemonCount);
		int count = jsonPokemonCount.getInt("count");

		// Make a GET request to the Pokemon API endpoint to get the URLs of all
		// available Pokemon
		url = "https://pokeapi.co/api/v2/pokemon?limit=" + count;
		String responsePokemonURL = restTemplate.getForObject(url, String.class);
		JSONObject jsonPokemonURL = new JSONObject(responsePokemonURL);
		JSONArray resultsArray = jsonPokemonURL.getJSONArray("results");
		List<PokemonURL> pokemonUrlList = new ArrayList<>();
		for (int i = 0; i < resultsArray.length(); i++) {
			JSONObject resultObject = resultsArray.getJSONObject(i);
			PokemonURL result = new PokemonURL(resultObject.getString("name"), resultObject.getString("url"));
			pokemonUrlList.add(result);
		}

		return pokemonUrlList;

	}

	/**
	 * Returns a list of Pokemon objects, each containing data for a Pokemon in the
	 * Pokemon API
	 * 
	 * @param pokemonUrlList
	 * @return PokemonList
	 */
	public List<Pokemon> getAllPokemon(List<PokemonURL> pokemonUrlList) {
		List<Pokemon> pokemonList = new ArrayList<Pokemon>();
		for (PokemonURL pokemonURL : pokemonUrlList) {
			// Make a GET request to the URL of the current Pokemon
			String responsePokemon = restTemplate.getForObject(pokemonURL.getUrl(), String.class);
			JSONObject jsonPokemon = new JSONObject(responsePokemon);
			String name = jsonPokemon.getString("name");
			int base_experience = 0;
			// Checks if any of the variables is null, and if it is, saves them as 0
			if (jsonPokemon.has("base_experience") && !jsonPokemon.isNull("base_experience")) {
				base_experience = jsonPokemon.getInt("base_experience");
			}
			int height = 0;
			if (jsonPokemon.has("height") && !jsonPokemon.isNull("height")) {
				height = jsonPokemon.getInt("height");
			}
			int weight = 0;
			if (jsonPokemon.has("weight") && !jsonPokemon.isNull("weight")) {
				weight = jsonPokemon.getInt("weight");
			}
			Pokemon pokemon = new Pokemon(name, base_experience, height, weight);
			pokemonList.add(pokemon);
		}
		return pokemonList;
	}

	/**
	 * Returns a list of JSON strings, each representing a Pokemon in the Pokemon
	 * API
	 * 
	 * @param pokemonList
	 * @param pokemonUrlList
	 * @return PokemonListJson
	 */
	public List<String> getPokemonListJson(List<Pokemon> pokemonList, List<PokemonURL> pokemonUrlList) {
		List<String> pokemonListJson = new ArrayList<>();
		for (Pokemon p : pokemonList) {
			// Find the corresponding PokemonURL for the current Pokemon
			Optional<PokemonURL> pokemonUrlEncontrado = pokemonUrlList.stream()
					.filter(pURL -> pURL.getName().equals(p.getName())).findFirst();
			// If a PokemonURL was found make a GET request to the URL of the current
			// Pokemon
			if (pokemonUrlEncontrado.isPresent()) {
				PokemonURL pokemonURL = pokemonUrlEncontrado.get();
				// Make a GET request to the URL of the current Pokemon
				String responsePokemon = restTemplate.getForObject(pokemonURL.getUrl(), String.class);
				pokemonListJson.add(responsePokemon);
			}
		}
		return pokemonListJson;
	}

	/**
	 * Returns a list of the 5 heaviest Pokemon in the Pokemon API
	 * 
	 * @param pokemonList
	 * @return PokemonList
	 */
	public List<Pokemon> getHeaviestPokemons(List<Pokemon> pokemonList) {
		// Sort the input list of Pokemon by weight in descending order
		pokemonList.sort((o1, o2) -> o2.compareToWeight(o1));
		return pokemonList.subList(0, Math.min(5, pokemonList.size()));
	}

	/**
	 * Returns a list of the highest Pokemon in the Pokemon API
	 * 
	 * @param pokemonList
	 * @return PokemonList
	 */
	public List<Pokemon> getHighestPokemons(List<Pokemon> pokemonList) {
		// Sort the input list of Pokemon by height in descending order
		pokemonList.sort((o1, o2) -> o2.compareToHeight(o1));
		return pokemonList.subList(0, Math.min(5, pokemonList.size()));
	}

	/**
	 * Returns a list of the most experienced Pokemon in the Pokemon API
	 * 
	 * @param pokemonList
	 * @return PokemonList
	 */
	public List<Pokemon> getBaseExperiencePokemons(List<Pokemon> pokemonList) {
		// Sort the input list of Pokemon by base experience in descending order
		pokemonList.sort((o1, o2) -> o2.compareTo(o1));
		return pokemonList.subList(0, Math.min(5, pokemonList.size()));
	}
}