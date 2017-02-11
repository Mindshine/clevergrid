package com.mindshine.clevergrid.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.mindshine.clevergrid.domain.Game;
import com.mindshine.clevergrid.repository.GameRepository;
import com.mindshine.clevergrid.service.GameService;
import com.mindshine.clevergrid.service.dto.GameDTO;
import com.mindshine.clevergrid.service.mapper.GameMapper;

/**
 * Service Implementation for managing Game.
 */
@Service
public class GameServiceImpl implements GameService{

	private final Logger log = LoggerFactory.getLogger(GameServiceImpl.class);

	@Inject
	private GameRepository gameRepository;

	@Inject
	private GameMapper gameMapper;

	/**
	 * Save a game.
	 *
	 * @param gameDTO the entity to save
	 * @return the persisted entity
	 */
	@Override
	public GameDTO save(GameDTO gameDTO) {
		this.log.debug("Request to save Game : {}", gameDTO);
		Game game;
		if (gameDTO.getId() == null) {
			game = this.gameMapper.gameDTOToGame(gameDTO);
		} else {
			game = this.gameRepository.findOne(gameDTO.getId());
			this.gameMapper.updateGameFromDto(gameDTO, game);
		}
		game = this.gameRepository.save(game);
		return this.gameMapper.gameToGameDTO(game);
	}

	/**
	 *  Get all the games.
	 *
	 *  @return the list of entities
	 */
	@Override
	public List<GameDTO> findAll() {
		this.log.debug("Request to get all Games");
		return this.gameRepository.findAll().stream()
				.map(this.gameMapper::gameToGameDTO)
				.collect(Collectors.toCollection(LinkedList::new));

	}

	/**
	 *  Get one game by id.
	 *
	 *  @param id the id of the entity
	 *  @return the entity
	 */
	@Override
	public GameDTO findOne(String id) {
		this.log.debug("Request to get Game : {}", id);
		Game game = this.gameRepository.findOne(id);
		return this.gameMapper.gameToGameDTO(game);
	}

	/**
	 *  Delete the  game by id.
	 *
	 *  @param id the id of the entity
	 */
	@Override
	public void delete(String id) {
		this.log.debug("Request to delete Game : {}", id);
		this.gameRepository.delete(id);
	}
}
