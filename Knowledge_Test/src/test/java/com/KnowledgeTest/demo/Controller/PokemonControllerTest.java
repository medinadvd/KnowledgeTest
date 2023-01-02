package com.KnowledgeTest.demo.Controller;

import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.KnowledgeTest.Service.PokemonService;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonControllerTest {

	@Autowired
	private WebTestClient webTestClient;

	@MockBean
	private PokemonService pokemonServiceMock;

	private List<String> expectedPokemonJson;

	@BeforeEach
	public void setUp() {
		expectedPokemonJson = new ArrayList<>();

	}

	@Test
	void getHighestPokemonTest() {
		//Arrange
		expectedPokemonJson.add("Pikachu lvl 100");
		when(pokemonServiceMock.getPokemonListJson(ArgumentMatchers.anyList(), ArgumentMatchers.anyList()))
				.thenReturn(expectedPokemonJson);
		// When
		webTestClient.get().uri("/pokemon/highest").exchange().expectStatus().isOk().expectBody(List.class)
				.isEqualTo(expectedPokemonJson);
	}

	@Test
	void getHeaviestPokemonTest() {
		//Arrange
		expectedPokemonJson.add("Pikachu lvl 56");
		when(pokemonServiceMock.getPokemonListJson(ArgumentMatchers.anyList(), ArgumentMatchers.anyList()))
				.thenReturn(expectedPokemonJson);
		// When
		webTestClient.get().uri("/pokemon/heaviest").exchange().expectStatus().isOk().expectBody(List.class)
				.isEqualTo(expectedPokemonJson);
	}

	@Test
	void getBaseExperiencePokemonTest() {
		//Arrange
		expectedPokemonJson.add("Pikachu lvl 2");
		when(pokemonServiceMock.getPokemonListJson(ArgumentMatchers.anyList(), ArgumentMatchers.anyList()))
				.thenReturn(expectedPokemonJson);
		// When
		webTestClient.get().uri("/pokemon/more_base_experience").exchange().expectStatus().isOk().expectBody(List.class)
				.isEqualTo(expectedPokemonJson);
	}


}