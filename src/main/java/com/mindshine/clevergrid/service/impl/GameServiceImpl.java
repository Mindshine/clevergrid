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
    public GameDTO save(GameDTO gameDTO) {
        log.debug("Request to save Game : {}", gameDTO);
        Game game = gameMapper.gameDTOToGame(gameDTO);
        game = gameRepository.save(game);
        GameDTO result = gameMapper.gameToGameDTO(game);
        return result;
    }

    /**
     *  Get all the games.
     *  
     *  @return the list of entities
     */
    public List<GameDTO> findAll() {
        log.debug("Request to get all Games");
        List<GameDTO> result = gameRepository.findAll().stream()
            .map(gameMapper::gameToGameDTO)
            .collect(Collectors.toCollection(LinkedList::new));

        return result;
    }

    /**
     *  Get one game by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    public GameDTO findOne(String id) {
        log.debug("Request to get Game : {}", id);
        Game game = gameRepository.findOne(id);
        GameDTO gameDTO = gameMapper.gameToGameDTO(game);
        return gameDTO;
    }

    /**
     *  Delete the  game by id.
     *
     *  @param id the id of the entity
     */
    public void delete(String id) {
        log.debug("Request to delete Game : {}", id);
        gameRepository.delete(id);
    }
}
