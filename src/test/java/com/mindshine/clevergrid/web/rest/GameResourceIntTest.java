package com.mindshine.clevergrid.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.mindshine.clevergrid.ClevergridApp;
import com.mindshine.clevergrid.domain.Game;
import com.mindshine.clevergrid.repository.GameRepository;
import com.mindshine.clevergrid.service.GameService;
import com.mindshine.clevergrid.service.dto.GameDTO;
import com.mindshine.clevergrid.service.mapper.GameMapper;

/**
 * Test class for the GameResource REST controller.
 *
 * @see GameResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ClevergridApp.class)
public class GameResourceIntTest {

	private static final String DEFAULT_TITLE = "AAAAAAAAAA";
	private static final String UPDATED_TITLE = "BBBBBBBBBB";

	private static final Boolean DEFAULT_IS_PUBLIC = false;
	private static final Boolean UPDATED_IS_PUBLIC = true;

	@Inject
	private GameRepository gameRepository;

	@Inject
	private GameMapper gameMapper;

	@Inject
	private GameService gameService;

	@Inject
	private MappingJackson2HttpMessageConverter jacksonMessageConverter;

	@Inject
	private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

	private MockMvc restGameMockMvc;

	private Game game;

	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		GameResource gameResource = new GameResource();
		ReflectionTestUtils.setField(gameResource, "gameService", this.gameService);
		this.restGameMockMvc = MockMvcBuilders.standaloneSetup(gameResource)
				.setCustomArgumentResolvers(this.pageableArgumentResolver)
				.setMessageConverters(this.jacksonMessageConverter).build();
	}

	/**
	 * Create an entity for this test.
	 *
	 * This is a static method, as tests for other entities might also need it,
	 * if they test an entity which requires the current entity.
	 */
	public static Game createEntity() {
		Game game = new Game()
				.title(DEFAULT_TITLE)
				.isPublic(DEFAULT_IS_PUBLIC);
		return game;
	}

	@Before
	public void initTest() {
		this.gameRepository.deleteAll();
		this.game = createEntity();
	}

	@Test
	public void createGame() throws Exception {
		int databaseSizeBeforeCreate = this.gameRepository.findAll().size();

		// Create the Game
		GameDTO gameDTO = this.gameMapper.gameToGameDTO(this.game);

		this.restGameMockMvc.perform(post("/api/games")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(gameDTO)))
		.andExpect(status().isCreated());

		// Validate the Game in the database
		List<Game> gameList = this.gameRepository.findAll();
		assertThat(gameList).hasSize(databaseSizeBeforeCreate + 1);
		Game testGame = gameList.get(gameList.size() - 1);
		assertThat(testGame.getTitle()).isEqualTo(DEFAULT_TITLE);
		assertThat(testGame.isIsPublic()).isEqualTo(DEFAULT_IS_PUBLIC);
		assertThat(testGame.getCreatedDate()).isNotNull();
		assertThat(testGame.getCreatedBy()).isNotNull();
		assertThat(testGame.getLastModifiedDate()).isNotNull();
		assertThat(testGame.getLastModifiedBy()).isNotNull();
	}

	@Test
	public void createGameWithExistingId() throws Exception {
		int databaseSizeBeforeCreate = this.gameRepository.findAll().size();

		// Create the Game with an existing ID
		Game existingGame = new Game();
		existingGame.setId("existing_id");
		GameDTO existingGameDTO = this.gameMapper.gameToGameDTO(existingGame);

		// An entity with an existing ID cannot be created, so this API call must fail
		this.restGameMockMvc.perform(post("/api/games")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(existingGameDTO)))
		.andExpect(status().isBadRequest());

		// Validate the Alice in the database
		List<Game> gameList = this.gameRepository.findAll();
		assertThat(gameList).hasSize(databaseSizeBeforeCreate);
	}

	@Test
	public void getAllGames() throws Exception {
		// Initialize the database
		this.gameRepository.save(this.game);

		// Get all the gameList
		this.restGameMockMvc.perform(get("/api/games?sort=id,desc"))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.[*].id").value(hasItem(this.game.getId())))
		.andExpect(jsonPath("$.[*].title").value(hasItem(DEFAULT_TITLE.toString())))
		.andExpect(jsonPath("$.[*].isPublic").value(hasItem(DEFAULT_IS_PUBLIC.booleanValue())));
	}

	@Test
	public void getGame() throws Exception {
		// Initialize the database
		this.gameRepository.save(this.game);

		// Get the game
		this.restGameMockMvc.perform(get("/api/games/{id}", this.game.getId()))
		.andExpect(status().isOk())
		.andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
		.andExpect(jsonPath("$.id").value(this.game.getId()))
		.andExpect(jsonPath("$.title").value(DEFAULT_TITLE.toString()))
		.andExpect(jsonPath("$.isPublic").value(DEFAULT_IS_PUBLIC.booleanValue()));
	}

	@Test
	public void getNonExistingGame() throws Exception {
		// Get the game
		this.restGameMockMvc.perform(get("/api/games/{id}", Long.MAX_VALUE))
		.andExpect(status().isNotFound());
	}

	@Test
	public void updateGame() throws Exception {
		// Initialize the database
		this.gameRepository.save(this.game);
		int databaseSizeBeforeUpdate = this.gameRepository.findAll().size();

		// Update the game
		Game updatedGame = this.gameRepository.findOne(this.game.getId());
		updatedGame
		.title(UPDATED_TITLE)
		.isPublic(UPDATED_IS_PUBLIC);
		GameDTO gameDTO = this.gameMapper.gameToGameDTO(updatedGame);

		this.restGameMockMvc.perform(put("/api/games")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(gameDTO)))
		.andExpect(status().isOk());

		// Validate the Game in the database
		List<Game> gameList = this.gameRepository.findAll();
		assertThat(gameList).hasSize(databaseSizeBeforeUpdate);
		Game testGame = gameList.get(gameList.size() - 1);
		assertThat(testGame.getTitle()).isEqualTo(UPDATED_TITLE);
		assertThat(testGame.isIsPublic()).isEqualTo(UPDATED_IS_PUBLIC);
		assertThat(testGame.getCreatedDate()).isNotNull();
		assertThat(testGame.getCreatedBy()).isNotNull();
		assertThat(testGame.getLastModifiedDate()).isNotNull();
		assertThat(testGame.getLastModifiedBy()).isNotNull();
	}

	@Test
	public void updateNonExistingGame() throws Exception {
		int databaseSizeBeforeUpdate = this.gameRepository.findAll().size();

		// Create the Game
		GameDTO gameDTO = this.gameMapper.gameToGameDTO(this.game);

		// If the entity doesn't have an ID, it will be created instead of just being updated
		this.restGameMockMvc.perform(put("/api/games")
				.contentType(TestUtil.APPLICATION_JSON_UTF8)
				.content(TestUtil.convertObjectToJsonBytes(gameDTO)))
		.andExpect(status().isCreated());

		// Validate the Game in the database
		List<Game> gameList = this.gameRepository.findAll();
		assertThat(gameList).hasSize(databaseSizeBeforeUpdate + 1);
	}

	@Test
	public void deleteGame() throws Exception {
		// Initialize the database
		this.gameRepository.save(this.game);
		int databaseSizeBeforeDelete = this.gameRepository.findAll().size();

		// Get the game
		this.restGameMockMvc.perform(delete("/api/games/{id}", this.game.getId())
				.accept(TestUtil.APPLICATION_JSON_UTF8))
		.andExpect(status().isOk());

		// Validate the database is empty
		List<Game> gameList = this.gameRepository.findAll();
		assertThat(gameList).hasSize(databaseSizeBeforeDelete - 1);
	}
}
