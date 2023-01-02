package com.KnowledgeTest.demo.Service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import com.KnowledgeTest.Model.Pokemon;
import com.KnowledgeTest.Model.PokemonURL;
import com.KnowledgeTest.Service.PokemonService;

@SpringBootTest
class PokemonServiceTest {

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private PokemonService pokemonService;

	private List<PokemonURL> pokemonUrlList = new ArrayList<>();
	private List<Pokemon> pokemonList = new ArrayList<>();
	private List<Pokemon> pokemonNullList = new ArrayList<>();

	@BeforeEach
	void setUp() {
		pokemonUrlList.add(new PokemonURL("bulbasaur", "https://pokeapi.co/api/v2/pokemon/1/"));
		pokemonUrlList.add(new PokemonURL("ivysaur", "https://pokeapi.co/api/v2/pokemon/22/"));
		pokemonUrlList.add(new PokemonURL("venusaur", "https://pokeapi.co/api/v2/pokemon/53/"));

		pokemonList.add(new Pokemon("bulbasaur", 44, 7, 69));
		pokemonList.add(new Pokemon("ivysaur", 42, 30, 111));
		pokemonList.add(new Pokemon("venusaur", 206, 40, 76));

		pokemonNullList.add(new Pokemon("bulbasaur", 0, 7, 69));
		pokemonNullList.add(new Pokemon("ivysaur", 142, 0, 130));
		pokemonNullList.add(new Pokemon("venusaur", 236, 20, 0));
	}

	@Test
	void getAllPokemonURLTest() {
		// Arrange
		String pokemonURLCountResponse = "{\"count\":3}";
		String pokemonURLResponse = "{\"results\":[{\"name\":\"bulbasaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/1/\"},"
				+ "{\"name\":\"ivysaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/22/\"},"
				+ "{\"name\":\"venusaur\",\"url\":\"https://pokeapi.co/api/v2/pokemon/53/\"}]}";

		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon", String.class))
				.thenReturn(pokemonURLCountResponse);
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon?limit=3", String.class))
				.thenReturn(pokemonURLResponse);

		// Act
		List<PokemonURL> actualPokemonURL = pokemonService.getAllPokemonURL();

		// Assert
		assertThat(actualPokemonURL).isEqualTo(pokemonUrlList);
	}

	@Test
	void getAllPokemonTest() {
		// Arrange
		String bulbasaurResponse = "{\"name\":\"bulbasaur\",\"base_experience\":44,\"height\":7,\"weight\":69}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/1/", String.class))
				.thenReturn(bulbasaurResponse);
		String ivysaurResponse = "{\"name\":\"ivysaur\",\"base_experience\":42,\"height\":30,\"weight\":111}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/22/", String.class))
				.thenReturn(ivysaurResponse);
		String venusaurResponse = "{\"name\":\"venusaur\",\"base_experience\":206,\"height\":40,\"weight\":76}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/53/", String.class))
				.thenReturn(venusaurResponse);

		// Act
		List<Pokemon> actualPokemon = pokemonService.getAllPokemon(pokemonUrlList);

		// Assert
		assertThat(actualPokemon).isEqualTo(pokemonList);
	}

	@Test
	void getAllPokemonNullVariablesTest() {
		// Arrange
		String bulbasaurResponse = "{\"name\":\"bulbasaur\",\"height\":7,\"weight\":69}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/1/", String.class))
				.thenReturn(bulbasaurResponse);
		String ivysaurResponse = "{\"name\":\"ivysaur\",\"base_experience\":142,\"weight\":130}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/22/", String.class))
				.thenReturn(ivysaurResponse);
		String venusaurResponse = "{\"name\":\"venusaur\",\"base_experience\":236,\"height\":20}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/53/", String.class))
				.thenReturn(venusaurResponse);

		// Act
		List<Pokemon> actualPokemon = pokemonService.getAllPokemon(pokemonUrlList);

		// Assert
		assertThat(actualPokemon).isEqualTo(pokemonNullList);
	}
	
	@Test
	void getAllPokemonJsonTest() {
		// Arrange
		List<String> expectedPokemonJson = new ArrayList<>();
		expectedPokemonJson.add("{\"name\":\"bulbasaur\",\"base_experience\":44,\"height\":7,\"weight\":69}");
		expectedPokemonJson.add("{\"name\":\"ivysaur\",\"base_experience\":42,\"height\":30,\"weight\":111}");
		expectedPokemonJson.add("{\"name\":\"venusaur\",\"base_experience\":206,\"height\":40,\"weight\":76}");
		
		String bulbasaurResponse = "{\"name\":\"bulbasaur\",\"base_experience\":44,\"height\":7,\"weight\":69}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/1/", String.class))
				.thenReturn(bulbasaurResponse);
		String ivysaurResponse = "{\"name\":\"ivysaur\",\"base_experience\":42,\"height\":30,\"weight\":111}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/22/", String.class))
				.thenReturn(ivysaurResponse);
		String venusaurResponse = "{\"name\":\"venusaur\",\"base_experience\":206,\"height\":40,\"weight\":76}";
		when(restTemplate.getForObject("https://pokeapi.co/api/v2/pokemon/53/", String.class))
				.thenReturn(venusaurResponse);
		
		// Act
		List<String> actualPokemonJson = pokemonService.getPokemonListJson(pokemonList, pokemonUrlList);

		// Assert
		assertThat(actualPokemonJson).isEqualTo(expectedPokemonJson);
	}

	@Test
	void getHeaviestPokemonTest() {
		// Arrange
		List<Pokemon> PokemonSortList = new ArrayList<>();
		PokemonSortList.add(new Pokemon("ivysaur", 42, 30, 111));
		PokemonSortList.add(new Pokemon("venusaur", 206, 40, 76));
		PokemonSortList.add(new Pokemon("bulbasaur", 44, 7, 69));
		
		// Act
		List<Pokemon> actualPokemon = pokemonService.getHeaviestPokemons(pokemonList);
		
		// Assert
		assertThat(actualPokemon).isEqualTo(PokemonSortList);
	}

	@Test
	void getHighestPokemonTest() {
		// Arrange
		List<Pokemon> PokemonSortList = new ArrayList<>();
		PokemonSortList.add(new Pokemon("venusaur", 206, 40, 76));
		PokemonSortList.add(new Pokemon("ivysaur", 42, 30, 111));
		PokemonSortList.add(new Pokemon("bulbasaur", 44, 7, 69));

		// Act
		List<Pokemon> actualPokemon = pokemonService.getHighestPokemons(pokemonList);
		
		// Assert
		assertThat(actualPokemon).isEqualTo(PokemonSortList);
	}

	@Test
	void getBaseExperiencePokemonTest() {
		// Arrange
		List<Pokemon> PokemonSortList = new ArrayList<>();
		PokemonSortList.add(new Pokemon("venusaur", 206, 40, 76));
		PokemonSortList.add(new Pokemon("bulbasaur", 44, 7, 69));
		PokemonSortList.add(new Pokemon("ivysaur", 42, 30, 111));	

		// Act
		List<Pokemon> actualPokemon = pokemonService.getBaseExperiencePokemons(pokemonList);
		
		// Assert
		assertThat(actualPokemon).isEqualTo(PokemonSortList);
	}

}