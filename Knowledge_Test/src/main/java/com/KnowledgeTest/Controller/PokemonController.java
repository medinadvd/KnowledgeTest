package com.KnowledgeTest.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.KnowledgeTest.Model.Pokemon;
import com.KnowledgeTest.Model.PokemonURL;
import com.KnowledgeTest.Service.PokemonService;

@RestController
public class PokemonController {

	private PokemonService pokemonService;
	private List<PokemonURL> pokemonUrlList;
	private List<Pokemon> pokemonList;
	private List<String> checker;

	/**
	 * 
	 * @param pokemonService
	 */
	public PokemonController(PokemonService pokemonService) {
		this.pokemonService = pokemonService;
		getPokemonList();
	}

	/**
	 * 
	 * Initializes the pokemonUrlList and pokemonList lists. If an exception occurs
	 * during the process, it initializes the checker list with an error message.
	 */
	public void getPokemonList() {
		try {
			pokemonUrlList = pokemonService.getAllPokemonURL();
			pokemonList = pokemonService.getAllPokemon(pokemonUrlList);
		} catch (Exception e) {
			checker = new ArrayList<>();
			checker.add("Could not contact the PokeAPI");
		}
	}

	/**
	 * Returns a list of Pokemons sorted by height. If there has been an error
	 * trying to obtain the Pokemon information, it returns an error message instead
	 * of the list.
	 * 
	 * @return PokemonListJson
	 */
	@GetMapping("/pokemon/highest")
	public List<String> getHighestPokemon() {
		if (checker == null) {
			List<Pokemon> pokemonHighestList = pokemonService.getHighestPokemons(pokemonList);
			return pokemonService.getPokemonListJson(pokemonHighestList, pokemonUrlList);
		}
		return checker;
	}

	/**
	 * Returns a list of Pokemons sorted by weight. If there has been an error
	 * trying to obtain the Pokemon information, it returns an error message instead
	 * of the list.
	 * 
	 * @return
	 */
	@GetMapping("/pokemon/heaviest")
	public List<String> getHeaviestPokemon() {
		if (checker == null) {
			List<Pokemon> pokemonHeaviestList = pokemonService.getHeaviestPokemons(pokemonList);
			return pokemonService.getPokemonListJson(pokemonHeaviestList, pokemonUrlList);
		}
		return checker;
	}

	/**
	 * Returns a list of Pokemons sorted by base experience. If there has been an
	 * error trying to obtain the Pokemon information, it returns an error message
	 * instead of the list.
	 * 
	 * @return
	 */
	@GetMapping("/pokemon/more_base_experience")
	public List<String> getBaseExperiencePokemon() {
		if (checker == null) {
			List<Pokemon> pokemonBaseExperienceList = pokemonService.getBaseExperiencePokemons(pokemonList);
			return pokemonService.getPokemonListJson(pokemonBaseExperienceList, pokemonUrlList);
		}
		return checker;
	}

	/**
	 * Refresh the list of Pokemons URL and Pokemon
	 * 
	 * @return String with success or fail message
	 */
	@GetMapping("/pokemon/refresh")
	public String getRefreshPokemonList() {
		getPokemonList();
		if (checker == null) {
			return ("List refreshed succesfully");
		}
		return checker.toString();
	}

}