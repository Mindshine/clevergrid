package com.mindshine.clevergrid.service.mapper;

import java.util.List;

import org.mapstruct.Mapper;

import com.mindshine.clevergrid.domain.Game;
import com.mindshine.clevergrid.service.dto.GameDTO;

/**
 * Mapper for the entity Game and its DTO GameDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface GameMapper {

    GameDTO gameToGameDTO(Game game);

    List<GameDTO> gamesToGameDTOs(List<Game> games);

    Game gameDTOToGame(GameDTO gameDTO);

    List<Game> gameDTOsToGames(List<GameDTO> gameDTOs);
}
