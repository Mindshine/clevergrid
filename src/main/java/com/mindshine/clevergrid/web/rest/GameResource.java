package com.mindshine.clevergrid.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.annotation.Timed;
import com.mindshine.clevergrid.service.GameService;
import com.mindshine.clevergrid.service.dto.GameDTO;
import com.mindshine.clevergrid.web.rest.util.HeaderUtil;

/**
 * REST controller for managing Game.
 */
@RestController
@RequestMapping("/api")
public class GameResource {

    private final Logger log = LoggerFactory.getLogger(GameResource.class);
        
    @Inject
    private GameService gameService;

    /**
     * POST  /games : Create a new game.
     *
     * @param gameDTO the gameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new gameDTO, or with status 400 (Bad Request) if the game has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/games")
    @Timed
    public ResponseEntity<GameDTO> createGame(@RequestBody GameDTO gameDTO) throws URISyntaxException {
        log.debug("REST request to save Game : {}", gameDTO);
        if (gameDTO.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("game", "idexists", "A new game cannot already have an ID")).body(null);
        }
        GameDTO result = gameService.save(gameDTO);
        return ResponseEntity.created(new URI("/api/games/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("game", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /games : Updates an existing game.
     *
     * @param gameDTO the gameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated gameDTO,
     * or with status 400 (Bad Request) if the gameDTO is not valid,
     * or with status 500 (Internal Server Error) if the gameDTO couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/games")
    @Timed
    public ResponseEntity<GameDTO> updateGame(@RequestBody GameDTO gameDTO) throws URISyntaxException {
        log.debug("REST request to update Game : {}", gameDTO);
        if (gameDTO.getId() == null) {
            return createGame(gameDTO);
        }
        GameDTO result = gameService.save(gameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("game", gameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /games : get all the games.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of games in body
     */
    @GetMapping("/games")
    @Timed
    public List<GameDTO> getAllGames() {
        log.debug("REST request to get all Games");
        return gameService.findAll();
    }

    /**
     * GET  /games/:id : get the "id" game.
     *
     * @param id the id of the gameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the gameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/games/{id}")
    @Timed
    public ResponseEntity<GameDTO> getGame(@PathVariable String id) {
        log.debug("REST request to get Game : {}", id);
        GameDTO gameDTO = gameService.findOne(id);
        return Optional.ofNullable(gameDTO)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /games/:id : delete the "id" game.
     *
     * @param id the id of the gameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/games/{id}")
    @Timed
    public ResponseEntity<Void> deleteGame(@PathVariable String id) {
        log.debug("REST request to delete Game : {}", id);
        gameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("game", id.toString())).build();
    }

}
